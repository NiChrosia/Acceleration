package acceleration.graphics

import arc.graphics.Color
import acceleration.math.Mathm
import arc.util.Tmp

open class Colorm {
    companion object {
        fun mix(a: Color, b: Color): Color {
            return Tmp.c1.set(Mathm.avg(a.r, b.r), Mathm.avg(a.g, b.g), Mathm.avg(a.b, b.b))
        }
    }
}