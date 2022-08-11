package org.ii.bots.raycasting

data class Ray(val direction: Vector3, val origin: Point3) {
    fun pointAt(t: Double): Point3 {
        val d = direction.normalize()
        return Point3(origin.x + d.x * t, origin.y + d.y * t, origin.z + d.z * t)
    }
}