package org.ii.bots.arena

object ArenaManager {
    private const val amount = 3

    private var arenaList = ArrayList<Arena>()

    init {
        arenaList = java.util.ArrayList()
        for (i in 1..amount) {
            val arena = Arena(i)
            arenaList.add(arena)
        }
    }

    fun refreshArenas() {
        arenaList.forEach { it.refresh() }
    }
}