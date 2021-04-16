package acceleration.graphics

import arc.graphics.Color
import acceleration.math.Mathm

open class Colorm {
    open fun mix(a: Color, b: Color): Color {
        return Color(Mathm().avg(a.r, b.r), Mathm().avg(a.g, b.g), Mathm().avg(a.b, b.b))
    }
}