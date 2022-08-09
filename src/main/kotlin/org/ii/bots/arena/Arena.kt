package org.ii.bots.arena

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.ii.bots.util.Utils
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.name
import kotlin.io.path.pathString

class Arena(id: Int) {
    var available: Boolean = true

    var world: World = Utils.getVoidWorld("BOTS_arena_$id")

    private var id: Int
    private var map: Map
    private var spawnLocations: ArrayList<Location> = java.util.ArrayList()

    init {
        this.id = id
        val files: List<Path> =
            Files.list(Arenas.path).filter { !it.fileName.fileName.name.endsWith('L') }.toList()
        files.forEach { print(it.fileName) }
        map = if (files.isNotEmpty()) {
            Map(files[id % files.size], world)
        } else {
            Map(Files.createFile(Path.of("${Arenas.path.pathString}/empty_arena")), world)
        }
        val p = Path.of("${map.source}L")
        if (Files.exists(p)) {
            Files.lines(p).forEach {
                val a = it.split(" ")
                spawnLocations.add(
                    Location(
                        world,
                        a[0].toDouble(),
                        a[1].toDouble(),
                        a[2].toDouble(),
                        a[3].toFloat(),
                        a[4].toFloat()
                    )
                )
                print(spawnLocations.toString())
            }
        } else {
            spawnLocations.add(Location(world, 0.0, 0.0, 0.0, 0F, 0F))
        }
    }

    fun refresh() {
        Bukkit.unloadWorld(world, false)
        world = Utils.getVoidWorld("BOTS_arena_$id")
        map.placeBlocks(world)
    }

    fun sendPlayers(players: ArrayList<Player>) {
        print(spawnLocations.toString())
        players.forEachIndexed { i, p -> p.teleport(spawnLocations[i % spawnLocations.size]) }
    }
}