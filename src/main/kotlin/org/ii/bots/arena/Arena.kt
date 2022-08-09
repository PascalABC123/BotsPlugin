package org.ii.bots.arena

import org.bukkit.World
import org.ii.bots.util.Utils

class Arena(id: Int) {
    private var id: Int

    private lateinit var world: World

    init {
        this.id = id
    }

    fun refresh() {
        this.world = Utils.getVoidWorld("BOTS_arena_$id")
    }
}