package org.ii.bots

import org.bukkit.entity.Player

object GlobalCmdExecutor {
    @Cmd("help", "", 0, "bots.player", ["hlp, hl, hel, he, h"])
    fun onHelpCmd(player: Player, args: Array<String>) {
        Main.instance?.help(player)
    }
}