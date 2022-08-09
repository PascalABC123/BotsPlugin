package org.ii.bots.arena

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import java.nio.file.Files
import java.nio.file.Path


class Map(source: Path, world: World) {
    var source = source

    private val blocks = ArrayList<StructureBlock>()

    init {
        Files.lines(source).forEach {
            val a = it.split(" ")
            blocks.add(StructureBlock(a[0].toShort(), a[1].toShort(), a[2].toShort(), a[3].toInt()))
        }
        placeBlocks(world)
    }

    fun placeBlocks(world: World) {
        blocks.forEach {
            Location(world, it.x.toDouble(), it.y.toDouble(), it.z.toDouble()).block.type =
                Material.getMaterial(it.blockId)
        }
    }

    class StructureBlock(x: Short, y: Short, z: Short, blockId: Int) {
        val x: Short
        val y: Short
        val z: Short
        val blockId: Int

        init {
            this.x = x
            this.y = y
            this.z = z
            this.blockId = blockId
        }
    }
}