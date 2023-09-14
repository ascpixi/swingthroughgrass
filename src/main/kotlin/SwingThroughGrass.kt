package com.github.ascpixi.stg

import org.bstats.bukkit.Metrics
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.java.JavaPlugin
import java.lang.IllegalStateException

class SwingThroughGrass : JavaPlugin() {
    companion object {
        var metrics: Metrics? = null
        var mainListener: GrassSwingListener? = null
    }

    fun reload() {
        reloadConfig()
        mainListener?.reload()
    }

    private fun requireCommandExists(name: String): PluginCommand {
        return getCommand(name)
            ?: throw IllegalStateException("The command '$name' isn't registered in the plugin.yml file.")
    }

    override fun onEnable() {
        metrics = Metrics(this, 14356)

        saveDefaultConfig()
        mainListener = GrassSwingListener(this);
        server.pluginManager.registerEvents(mainListener!!, this)

        requireCommandExists("swingthroughgrass").setExecutor(MainCommandHandler(this))
    }
}