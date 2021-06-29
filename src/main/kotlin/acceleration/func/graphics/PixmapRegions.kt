package acceleration.func.graphics

import arc.graphics.Color
import arc.graphics.Pixmap
import arc.graphics.g2d.PixmapRegion
import arc.math.geom.Vec2

fun PixmapRegion.getColor(x: Int, y: Int): Color {
    return Color(get(x, y))
}

fun PixmapRegion.getColor(pos: Vec2): Color {
    return getColor(pos.x.toInt(), pos.y.toInt())
}

/** Merge two pixmaps.
 * @param other The other pixmap to merge with.
 * @param f The value to use for linear interpolation between colors. */
fun PixmapRegion.merge(other: PixmapRegion, f: Float): Pixmap {
    val out = Pixmap(width, height)

    for (x in 0 until width) {
        for (y in 0 until height) {
            val pos = Vec2(x.toFloat(), y.toFloat())

            out.set(x, y, getColor(pos).lerp(other.getColor(pos), f))
        }
    }

    return out
}