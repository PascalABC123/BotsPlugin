package org.ii.bots

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.ii.bots.arena.Arenas
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
        dataFolder.mkdirs()

        Arenas.refreshArenas()
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

        val f = cmdList.filter { (sender as Player).hasPermission(it.getAnnotation(Cmd::class.java).permission) }
            .find { args[0] == it.getAnnotation(Cmd::class.java).name || args[0] in it.getAnnotation(Cmd::class.java).aliases }

        if (f == null) {
            help(sender as Player)
            return true
        }

        if (f.getAnnotation(Cmd::class.java).minArgs >= args.size || !(f.invoke(
                GlobalCmdExecutor,
                sender as Player,
                Utils.removeFirstElement(args)
            ) as Boolean)
        ) {
            sender?.sendMessage(f.getAnnotation(Cmd::class.java).help)
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender?,
        command: Command?,
        alias: String?,
        args: Array<out String>?
    ): MutableList<String> {

        val permArgs = ArrayList<String>()

        if (args?.size == 1) {
            cmdList.filter {
                (sender as Player).hasPermission(it.getAnnotation(Cmd::class.java).permission)
                        && it.getAnnotation(Cmd::class.java).name.startsWith(args[0])
            }.forEach {
                permArgs.add(it.getAnnotation(Cmd::class.java).name)
            }
        }

        return permArgs

    }

    fun help(player: Player) {
        player.sendMessage("")
        player.sendMessage("List of commands for §2§lBots:")
        cmdList.filter {
            player.hasPermission(it.getAnnotation(Cmd::class.java).permission)
        }.forEach {
            player.sendMessage("- /bots §2${it.getAnnotation(Cmd::class.java).name} §f${it.getAnnotation(Cmd::class.java).args}")
        }
        player.sendMessage("")
    }
}
