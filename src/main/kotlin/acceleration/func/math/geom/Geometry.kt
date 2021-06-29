package acceleration.func.math.geom

import arc.math.geom.Vec2
import arc.util.Tmp

fun normalPoly(pointCount: Int, distance: Float, offsetRotation: Int = 0): List<Vec2> {
    return (1..pointCount).map {
        val vec = Tmp.v1.set(Vec2())
        vec.trns(((it * (360f / pointCount)) + offsetRotation) % 360, distance)
    }
}