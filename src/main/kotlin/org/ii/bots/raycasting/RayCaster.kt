package org.ii.bots.raycasting

import kotlin.math.cos
import kotlin.math.sin

object RayCaster {
    fun getVector3(yaw: Double, pitch: Double): Vector3 {
        val radius = cos(Math.toRadians(-pitch))
        return Vector3(radius * sin(Math.toRadians(-yaw)), sin(Math.toRadians(-pitch)), radius * cos(Math.toRadians(-yaw)))
    }
}