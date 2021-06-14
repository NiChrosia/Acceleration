package acceleration.math.geom

import arc.math.geom.Vec2
import arc.struct.Seq
import arc.util.Tmp

class Geometrym {
    companion object {
        fun normalPoly(pointCount: Int, distance: Float): Seq<Vec2> {
            return normalPoly(pointCount, distance, 0)
        }

        fun normalPoly(pointCount: Int, distance: Float, offsetRotation: Int): Seq<Vec2> {
            val pointSeq = Seq<Vec2>()

            for (i in 1..pointCount) {
                val vec = Tmp.v1.set(Vec2())
                vec.trns(((i * (360f / pointCount)) + offsetRotation) % 360, distance)

                pointSeq.add(vec)
            }

            return pointSeq
        }
    }
}