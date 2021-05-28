package acceleration.graphics

import arc.Core
import arc.graphics.Color
import arc.graphics.Pixmap
import arc.graphics.Texture
import arc.graphics.g2d.TextureRegion
import arc.util.Tmp

open class Drawm {
    companion object {
        /** Lerps 2 TextureRegions. */
        fun blendSprites(a: TextureRegion, b: TextureRegion, f: Float, name: String): TextureRegion {
            val r1 = Core.atlas.getPixmap(a)
            val r2 = Core.atlas.getPixmap(b)
            val color1 = Tmp.c1.set(Color())
            val color2 = Tmp.c1.set(Color())
            val out = Pixmap(r1.width, r1.height, r1.pixmap.format)

            out.blending = Pixmap.Blending.none

            for (x in 0 until r1.width) {
                for (y in 0 until r1.height) {
                    r1.getPixel(x, y, color1)
                    r2.getPixel(x, y, color2)
                    out.draw(x, y, color1.lerp(color2, f))
                }
            }

            val texture = Texture(out)

            return Core.atlas.addRegion(
                name + "-blended-" + (f * 100).toInt(),
                TextureRegion(texture)
            )
        }
    }
}