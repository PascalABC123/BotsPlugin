package org.ii.bots

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.ii.bots.arena.ArenaManager
import org.ii.bots.util.Utils
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import java.lang.reflect.Method

class Main : JavaPlugin(), CommandExecutor {

    companion object {
        var instance: Main? = null
    }

    init {
        instance = this
    }

    private lateinit var cmdList: HashSet<Method>

    override fun onEnable() {
        ArenaManager.refreshArenas()
        getCommand("bots").executor = this

        val reflections = Reflections("org.ii.bots", MethodAnnotationsScanner())
        cmdList = reflections.getMethodsAnnotatedWith(Cmd::class.java) as HashSet<Method>
    }

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {

        if (args == null || args.isEmpty() || args[0] == "") {
            help(sender as Player)
            return true
        }

        // WTF IS THIS??????????? (извините, я больше по спортивному программированию)
        (cmdList.filter { (sender as Player).hasPermission(it.getAnnotation(Cmd::class.java).permission) }
            .find { it.getAnnotation(Cmd::class.java).name == args[0] || args[0] in it.getAnnotation(Cmd::class.java).aliases }
            ?: return help(sender as Player)).invoke(
            GlobalCmdExecutor,
            sender as Player,
            Utils.removeFirstElement(args)
        )

        return true
    }

    override fun onTabComplete(
        sender: CommandSender?,
        command: Command?,
        alias: String?,
        args: Array<out String>?
    ): MutableList<String> {

        val permArgs = ArrayList<String>()

        if (args == null || args.isEmpty() || args[0] == "") {
            cmdList.filter {
                (sender as Player).hasPermission(it.getAnnotation(Cmd::class.java).permission)
            }.forEach {
                permArgs.add(it.getAnnotation(Cmd::class.java).name)
            }
        }

        return permArgs

    }

    fun help(player: Player): Boolean {
        player.sendMessage("")
        player.sendMessage("List of commands for §2§lBots:")
        cmdList.filter {
            player.hasPermission(it.getAnnotation(Cmd::class.java).permission)
        }.forEach {
            player.sendMessage("- /bots §2${it.getAnnotation(Cmd::class.java).name}")
        }
        player.sendMessage("")
        return true
    }
}
