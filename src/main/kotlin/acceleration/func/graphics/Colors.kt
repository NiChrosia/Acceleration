package acceleration.func.graphics

import arc.graphics.Color
import acceleration.func.math.avg
import arc.util.Tmp

fun Color.mix(other: Color): Color {
    return Tmp.c1.set(avg(r, other.r), avg(g, other.g), avg(b, other.b))
}