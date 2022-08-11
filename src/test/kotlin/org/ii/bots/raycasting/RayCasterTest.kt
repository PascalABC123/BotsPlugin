package org.ii.bots.raycasting

import org.junit.jupiter.api.Test

import java.text.DecimalFormat

internal class RayCasterTest {
    @Test
    fun getVector3() {
        val pitch: Float = 0F
        val yaw: Float = 90F
        val df = DecimalFormat("#.####")
        val ans: Vector3 = RayCaster.getVector3(yaw, pitch)
        println("${df.format(ans.x)} ${df.format(ans.y)} ${df.format(ans.z)}")
    }
}