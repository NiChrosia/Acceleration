package acceleration.math

import arc.math.geom.Vec2
import arc.struct.Seq

open class Mathm {
    companion object {
        fun avg(vararg args: Int): Int {
            var total = 0
            var totalCount = 0
            for (i in args) {
                totalCount++
                total += i
            }

            return total / totalCount
        }

        fun avg(vararg args: Float): Float {
            var total = 0f
            var totalCount = 0
            for (i in args) {
                totalCount++
                total += i
            }

            return total / totalCount
        }

        fun avg(vararg args: Double): Double {
            var total = 0.0
            var totalCount = 0
            for (i in args) {
                totalCount++
                total += i
            }

            return total / totalCount
        }
    }
}