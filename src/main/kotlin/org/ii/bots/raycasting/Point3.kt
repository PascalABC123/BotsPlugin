package org.ii.bots.raycasting

data class Point3(val x: Double, val y: Double, val z: Double) {
    operator fun Point3.plus(other: Point3): Point3 {
        return Point3(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    operator fun Point3.minus(other: Point3): Point3 {
        return Point3(this.x - other.x, this.y - other.y, this.z - other.z)
    }
}