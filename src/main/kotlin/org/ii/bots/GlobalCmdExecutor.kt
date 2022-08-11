package org.ii.bots

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.ii.bots.arena.Arenas
import org.ii.bots.raycasting.Point3
import org.ii.bots.raycasting.Ray
import org.ii.bots.raycasting.RayCaster
import java.nio.file.Path
import kotlin.io.path.appendText
import kotlin.io.path.createFile
import kotlin.io.path.notExists
import kotlin.io.path.writeText

object GlobalCmdExecutor {
    @Cmd("help", "", 0, "bots.player", ["hlp", "hl", "hel", "he", "h"], "")
    fun onHelpCmd(player: Player, args: Array<String>): Boolean {
        Main.instance?.help(player)
        return true
    }

    @Cmd("goto", "<Arena id>", 1, "bots.arena.select", ["gt", "tp", "arena"], "please type in args in this order: id")
    fun onGotoCmd(player: Player, args: Array<String>): Boolean {
        Arenas.getArenaById(args[0].toInt()).sendPlayers(arrayListOf(player))
        return true
    }

    @Cmd("savemap", "<Map name>", 1, "bots.map.save", ["ms"], "please type in args in this order: map name")
    fun onSaveMapCmd(player: Player, args: Array<String>): Boolean {
        val mapFile = Path.of("${Arenas.path}/${args[0].lowercase()}")
        if (mapFile.notExists()) {
            mapFile.createFile()
        }
        val w = player.location.world
        if (!w.name.startsWith("BOTS")) return false
        val sb = java.lang.StringBuilder()
        w.loadedChunks.forEach {
            for (x in 0..16)
                for (y in 0..256)
                    for (z in 0..16) {
                        val b = it.getBlock(x, y, z)
                        val l = b.location
                        val t = b.type
                        if (t != Material.AIR) {
                            sb.append("${l.x.toInt()} ${l.y.toInt()} ${l.z.toInt()} ${t.id}\n")
                        }
                    }
        }
        mapFile.writeText(sb.toString())
        player.sendMessage("§l§2Successfully saved!")
        return true
    }

    @Cmd("addspawn", "<Map name>", 1, "bots.map.spawn", ["as"], "please type in args in this order: map name")
    fun onAddSpawnCmd(player: Player, args: Array<String>): Boolean {
        val mapFile = Path.of("${Arenas.path}/${args[0]}L")
        if (mapFile.notExists()) {
            mapFile.createFile()
        }
        val l = player.location
        mapFile.appendText("${l.x} ${l.y} ${l.z} ${l.yaw} ${l.pitch}\n")
        player.sendMessage("§l§2Successfully added!")
        return true
    }

    @Cmd("start", "", 0, "bots.start", ["s, go"], "")
    fun onStartCmd(player: Player, args: Array<String>): Boolean {
        return Arenas.sendToAvailableArena(arrayListOf(player))
    }

    @Cmd("test", "", 0, "bots.test", [], "")
    fun onTestCmd(player: Player, args: Array<String>): Boolean {
        val origin = Point3(player.eyeLocation.x, player.eyeLocation.y, player.eyeLocation.z)
        val direction = RayCaster.getVector3(player.eyeLocation.yaw, player.eyeLocation.pitch)
        player.sendMessage(RayCaster.getRayDistance(Ray(direction, origin), (Bukkit.getPlayer(args[0]) as CraftPlayer).handle.boundingBox).toString())
        return true
    }
}