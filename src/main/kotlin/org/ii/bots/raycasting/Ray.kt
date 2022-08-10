package org.ii.bots.raycasting

data class Ray(val direction: Vector3, val origin: Point3) {
    fun pointAt(t: Double): Point3 {
        return Point3(origin.x + direction.x * t, origin.y + direction.y * t, origin.z + direction.z * t)
    }
}