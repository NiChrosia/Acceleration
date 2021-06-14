package acceleration.graphics

import arc.Core
import arc.graphics.Color
import arc.graphics.Pixmap
import arc.graphics.Texture
import arc.graphics.g2d.TextureRegion
import arc.util.Tmp

fun TextureRegion.blend(other: TextureRegion, f: Float = 0.5f, name: String): TextureRegion {
    val r1 = Core.atlas.getPixmap(this)
    val r2 = Core.atlas.getPixmap(other)
    val color1 = Tmp.c1.set(Color())
    val color2 = Tmp.c1.set(Color())
    val out = Pixmap(r1.width, r1.height)

    for (x in 0 until r1.width) {
        for (y in 0 until r1.height) {
            r1.get(x, y, color1)
            r2.get(x, y, color2)
            out.set(x, y, color1.lerp(color2, f))
        }
    }

    val texture = Texture(out)

    return Core.atlas.addRegion(
        name + "-blended-" + (f * 100).toInt(),
        TextureRegion(texture)
    )
}