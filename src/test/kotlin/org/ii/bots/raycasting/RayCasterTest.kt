package org.ii.bots.raycasting

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.text.DecimalFormat

internal class RayCasterTest {
    @Test
    fun getVector3() {
        val pitch: Double = 0.0
        val yaw: Double = 90.0
        val df = DecimalFormat("#.####")
        val ans: Vector3 = RayCaster.getVector3(yaw, pitch)
        println("${df.format(ans.x)} ${df.format(ans.y)} ${df.format(ans.z)}")
    }
}