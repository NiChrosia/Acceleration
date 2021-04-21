package acceleration.math.geom

import arc.math.geom.Vec2
import arc.struct.Seq

class Geometrym {
    companion object {
        fun normalPoly(pointCount: Int, distance: Float): Seq<Vec2> {
            val pointSeq = Seq<Vec2>()

            for (i in 1..pointCount) {
                val vec = Vec2()

                vec.trns((i * (360f / pointCount)) % 360, distance)

                pointSeq.add(vec)
            }

            return pointSeq
        }

        fun normalPoly(pointCount: Int, distance: Float, offsetRotation: Int): Seq<Vec2> {
            val pointSeq = Seq<Vec2>()

            for (i in 1..pointCount) {
                val vec = Vec2()

                vec.trns(((i * (360f / pointCount)) + offsetRotation) % 360, distance)

                pointSeq.add(vec)
            }

            return pointSeq
        }
    }
}