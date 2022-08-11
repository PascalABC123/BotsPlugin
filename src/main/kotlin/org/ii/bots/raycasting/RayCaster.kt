package org.ii.bots.raycasting

import net.minecraft.server.v1_8_R3.AxisAlignedBB
import kotlin.math.*


object RayCaster {
    fun getVector3(yaw: Float, pitch: Float): Vector3 {
        val radius = cos(Math.toRadians((-pitch).toDouble()))
        return Vector3(radius * sin(Math.toRadians((-yaw).toDouble())), sin(Math.toRadians((-pitch).toDouble())), radius * cos(Math.toRadians(
            (-yaw).toDouble()
        )))
    }

    fun getIntersectionPoint(ray: Ray, aabb: AxisAlignedBB): Point3? {
        val clx = if(abs(aabb.a - ray.origin.x) < abs(aabb.d - ray.origin.x)) aabb.a else aabb.d
        val cly = if(abs(aabb.b - ray.origin.y) < abs(aabb.e - ray.origin.y)) aabb.b else aabb.e
        val clz = if(abs(aabb.c - ray.origin.z) < abs(aabb.f - ray.origin.z)) aabb.c else aabb.f

        val iX: Point3 = ray.pointAt(abs((ray.origin.x - clx) / ray.direction.x))
        val iY: Point3 = ray.pointAt(abs((ray.origin.y - cly) / ray.direction.y))
        val iZ: Point3 = ray.pointAt(abs((ray.origin.z - clz) / ray.direction.z))

        if(iX.y in aabb.b..aabb.e && iX.z in aabb.c..aabb.f) return iX
        if(iY.x in aabb.a..aabb.d && iY.z in aabb.c..aabb.f) return iY
        if(iZ.x in aabb.a..aabb.d && iZ.y in aabb.b..aabb.e) return iZ
        return null
    }

    fun getAllIntersectionVectors(ray: Ray, aabb: AxisAlignedBB): ArrayList<Vector3> {
        val start = Point3(0.0, 0.0, 0.0)
        val end = ray.direction
        val xMin = aabb.a - ray.origin.x
        val yMin = aabb.b - ray.origin.y
        val zMin = aabb.c - ray.origin.z
        val xMax = aabb.d - ray.origin.x
        val yMax = aabb.e - ray.origin.y
        val zMax = aabb.f - ray.origin.z

        /**
         * XZ projection:
         * aabb: maxX = d, minX = a, maxZ = f, minZ = c
         *      z(x) = c
         *      z(x) = f
         * ray: z(x) = kx + b
         * k (slope) = (z2 - z1) / (x2 - x1)
         * b = 0
         */

        val res: ArrayList<Vector3> = ArrayList()
        val kZ: Double = (end.z - start.z) / (end.x - start.x)
        val ky: Double = (end.y - start.y) / (end.x - start.x)
        val z1 = kZ * xMin
        val z2 = kZ * xMax
        val x1 = (1/kZ) * zMin
        val x2 = (1/kZ) * zMax
        if(z1 in zMin..zMax && (ky * xMin) in yMin..yMax) res.add(Vector3(z1, ky * xMin, xMin))
        if(z2 in zMin..zMax && (ky * xMax) in yMin..yMax) res.add(Vector3(z2, ky * xMax, xMax))
        if(x1 in xMin..xMax && (ky * x1) in yMin..yMax) res.add(Vector3(zMin, ky * x1, x1))
        if(x2 in xMin..xMax && (ky * x2) in yMin..yMax) res.add(Vector3(zMax, ky * x2, x2))
        return res
    }

    fun getFirstIntersection(ray: Ray, aabb: AxisAlignedBB): Point3? {
        val res = getAllIntersectionVectors(ray, aabb)

        if(res.size == 2) {
            if(res[0].getLength() < res[1].getLength()) {
                return ray.origin + res[0]
            }
            else {
                return ray.origin + res[1]
            }
        }
        else if(res.size == 1) {
            return ray.origin + res[0]
        }
        else {
            return null
        }
    }

    fun getRayDistance(ray: Ray, aabb: AxisAlignedBB): Double {
        val p = getIntersectionPoint(ray, aabb) ?: return 0.0
        return (p - ray.origin).toVector3().getLength()
    }
}