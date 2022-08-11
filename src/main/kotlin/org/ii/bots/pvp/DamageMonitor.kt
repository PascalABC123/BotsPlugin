package org.ii.bots.pvp

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.EventExecutor
import org.ii.bots.raycasting.Point3
import org.ii.bots.raycasting.Ray
import org.ii.bots.raycasting.RayCaster

object DamageMonitor : EventExecutor {
    override fun execute(listner: Listener?, event: Event?) {
        if(event !is EntityDamageByEntityEvent) {
            return
        }
        if(event.damager is Player) {
            val p = event.damager as Player
            val origin = Point3(p.eyeLocation.x, p.eyeLocation.y, p.eyeLocation.z)
            val direction = RayCaster.getVector3(p.eyeLocation.yaw, p.eyeLocation.pitch)
            val dist = RayCaster.getRayDistance(Ray(direction, origin), ((event.entity as CraftEntity).handle.boundingBox))
            p.sendMessage((event.entity.velocity.x * event.entity.velocity.x + event.entity.velocity.y * event.entity.velocity.y + event.entity.velocity.z * event.entity.velocity.z).toString())
        }
    }


}