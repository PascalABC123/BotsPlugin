package org.ii.bots.arena

import org.bukkit.entity.Player
import org.ii.bots.Main
import java.nio.file.Files
import java.nio.file.Path

object Arenas {
    val path: Path = Path.of("${Main.instance?.dataFolder?.toString()}/maps/")

    private const val amount = 3

    private var arenaList = ArrayList<Arena>()

    init {
        if (Files.notExists(path)) {
            Files.createDirectory(path)
        }
        arenaList = java.util.ArrayList()
        for (i in 1..amount) {
            val arena = Arena(i)
            arenaList.add(arena)
        }
    }

    fun refreshArenas() {
        arenaList.forEach { it.refresh() }
    }

    fun getArenaById(id: Int): Arena {
        return arenaList[id - 1]
    }

    fun sendToAvailableArena(players: ArrayList<Player>): Boolean {
        val arena = arenaList.find { it.available }
        if (arena == null) {
            players.forEach { it.sendMessage("No bot arenas available :(") }
            return false
        }
        arena.sendPlayers(players)
        return true
    }
}