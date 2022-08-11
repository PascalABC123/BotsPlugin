package org.ii.bots.raycasting

import kotlin.math.sqrt

data class Vector3(val x: Double, val y: Double, val z: Double) {

    fun getLength(): Double {
        return sqrt(getSquaredLength())
    }

    fun getSquaredLength(): Double {
        return x * x + y * y + z * z
    }

    fun normalize(): Vector3 {
        return this / getLength()
    }

    operator fun plus(other: Vector3): Vector3 {
        return Vector3(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    operator fun minus(other: Vector3): Vector3 {
        return Vector3(this.x - other.x, this.y - other.y, this.z - other.z)
    }

    operator fun times(t: Double): Vector3 {
        return Vector3(this.x * t, this.y * t, this.z * t)
    }

    operator fun div(t: Double): Vector3 {
        return this * (1.0 / t)
    }

    operator fun plus(other: Point3): Vector3 {
        return Vector3(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    operator fun minus(other: Point3): Vector3 {
        return Vector3(this.x - other.x, this.y - other.y, this.z - other.z)
    }

}