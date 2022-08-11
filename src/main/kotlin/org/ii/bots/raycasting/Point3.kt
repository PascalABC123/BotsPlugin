package org.ii.bots.raycasting

data class Point3(val x: Double, val y: Double, val z: Double) {
    fun toVector3(): Vector3 {
        return Vector3(x, y, z)
    }

    operator fun plus(other: Point3): Point3 {
        return Point3(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    operator fun minus(other: Point3): Point3 {
        return Point3(this.x - other.x, this.y - other.y, this.z - other.z)
    }

    operator fun plus(other: Vector3): Point3 {
        return Point3(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    operator fun minus(other: Vector3): Point3 {
        return Point3(this.x - other.x, this.y - other.y, this.z - other.z)
    }
}