package acceleration.graphics

import arc.Core
import arc.graphics.Texture
import arc.graphics.g2d.TextureRegion

/** Blend two `TextureRegion`s.
 * @param other The other region to blend with.
 * @param f The value for lerping colors.
 * @param name The name to use for the new atlas region. */
fun TextureRegion.blend(other: TextureRegion, f: Float = 0.5f, name: String): TextureRegion {
    val pixmap = Core.atlas.getPixmap(this)
    val otherPixmap = Core.atlas.getPixmap(other)

    val texture = TextureRegion(Texture(pixmap.merge(otherPixmap, f)))

    return Core.atlas.addRegion(
        "$name-blended-${f * 100}",
        texture
    )
}