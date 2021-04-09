package acceleration.graphics

import arc.Core
import arc.graphics.Color
import arc.graphics.Pixmap
import arc.graphics.Texture
import arc.graphics.g2d.TextureRegion

open class Drawm {
    /** Lerps 2 TextureRegions. */
    fun blendSprites(a: TextureRegion, b: TextureRegion, f: Float, name: String): TextureRegion {
        val r1 = Core.atlas.getPixmap(a)
        val r2 = Core.atlas.getPixmap(b)
        val out = Pixmap(r1.width, r1.height, r1.pixmap.format)
        out.blending = Pixmap.Blending.none
        val color1 = Color()
        val color2 = Color()
        for (x in 0 until r1.width) {
            for (y in 0 until r1.height) {
                r1.getPixel(x, y, color1)
                r2.getPixel(x, y, color2)
                out.draw(x, y, color1.lerp(color2, f))
            }
        }
        val texture = Texture(out)
        return Core.atlas.addRegion(name + "-blended-" + (f * 100).toInt(), TextureRegion(texture))
    }
}