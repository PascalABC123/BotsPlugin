package org.ii.bots.util

import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType

object Utils {
    fun getVoidWorld(name: String): World {
        val wc = WorldCreator(name)
        wc.type(WorldType.FLAT)
        wc.generatorSettings("2;0;1;")
        return wc.createWorld()
    }

    fun removeFirstElement(arr: Array<out String>): Array<String> {
        val l = arr.toMutableList()
        l.removeAt(0)
        return l.toTypedArray()
    }
}