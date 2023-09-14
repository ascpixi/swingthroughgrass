package com.github.ascpixi.stg

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class MainCommandHandler(private val plugin: SwingThroughGrass) : CommandExecutor {
    private fun rejectWrongUsage(sender: CommandSender, cmd: Command) {
        sender.sendMessage("${ChatColor.RED}Usage: ${cmd.usage}")
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (args.size != 1) {
            rejectWrongUsage(sender, cmd)
            return true
        }

        when (args[0].lowercase()) {
            "reload" -> {
                plugin.reload()
                sender.sendMessage("${ChatColor.GREEN}The plugin has been reloaded.")
            }
            "version" -> {
                sender.sendMessage(
                    "Running ${ChatColor.YELLOW}SwingThroughGrass v${plugin.description.version}${ChatColor.RESET}."
                )
            }
            else -> rejectWrongUsage(sender, cmd)
        }

        return true
    }
}