package acceleration.graphics

import arc.graphics.Color
import acceleration.math.Mathm
import arc.util.Tmp

fun Color.mix(other: Color): Color {
    return Tmp.c1.set(Mathm.avg(r, other.r), Mathm.avg(g, other.g), Mathm.avg(b, other.b))
}