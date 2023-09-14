package com.github.ascpixi.stg

import org.bukkit.FluidCollisionMode
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import java.util.*

class GrassSwingListener(private val plugin: SwingThroughGrass) : Listener {
    private val processedBlocks: EnumSet<Material> = EnumSet.noneOf(Material::class.java)

    init {
        reload()
    }

    /**
     * Reloads this listener.
     */
    fun reload() {
        val whitelist = plugin.config.getStringList("block-whitelist")

        processedBlocks.clear()
        for (materialName in whitelist) {
            processedBlocks.add(Material.getMaterial(materialName))
        }
    }

    private fun isWeapon(givenMaterial: Material): Boolean {
        return when (givenMaterial) {
            // Swords
            Material.WOODEN_SWORD, Material.STONE_SWORD, Material.GOLDEN_SWORD,
            Material.IRON_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD
                -> true

            // Axes
            Material.WOODEN_AXE, Material.STONE_AXE, Material.GOLDEN_AXE,
            Material.IRON_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE
                -> true

            else -> false
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onBlockBreak(ev: BlockBreakEvent) {
        val eq = ev.player.equipment ?: return

        if (
            ev.player.attackCooldown == 1f &&
            processedBlocks.contains(ev.block.type) &&
            isWeapon(eq.itemInMainHand.type)
        ) {
            val ray = ev.player.world.rayTrace(
                ev.player.eyeLocation,
                ev.player.location.direction,
                2.0,
                FluidCollisionMode.NEVER,
                true,
                0.5
            ) { e -> e.type.isAlive && e != ev.player && e != ev.player.vehicle } ?: return

            if (ray.hitEntity == null)
                return

            ev.player.attack(ray.hitEntity as LivingEntity)
        }
    }
}