package acceleration.type.item

import arc.Core
import arc.graphics.Color
import mindustry.type.Item
import arc.graphics.g2d.TextureRegion
import arc.util.Time
import mindustry.ui.Cicon
import arc.graphics.Texture
import arc.graphics.Pixmap
import arc.struct.Seq

open class AnimatedItem(name: String) : Item(name) {
    private val animIcon = TextureRegion()
    private var animRegions: Seq<TextureRegion> = Seq()

    private var animDelay = 3f
    private var animate = true

    /** Number of initial sprites. */
    var sprites = 10

    /** # of transition frames inserted between two sprites.  */
    var transition = 0

    //set in load()
    private var n = 0

    /** Lerps 2 TextureRegions.  */
    open fun blendSprites(a: TextureRegion?, b: TextureRegion?, f: Float, name: String): TextureRegion {
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

    override fun load() {
        super.load()
        val spriteArr = Seq<TextureRegion>()

        for (i in 0 until sprites) {
            spriteArr.add(Core.atlas.find(name + i, "$name-$i"))
        }

        n = sprites * (1 + transition)
        val animatedRegions = Seq<TextureRegion>()

        for (sprite in spriteArr) {
            val spriteName: String = sprite.asAtlas().name
            val spriteNumber: Int = spriteName.filter { it.isDigit() }.toInt()

            val lastSprite: TextureRegion = if (spriteNumber != 0) {
                spriteArr.get(spriteNumber - 1)
            } else {
                sprite
            }

            animatedRegions.add(sprite)

            for (t in 0 until transition) {
                val f = (t / (transition + 1)).toFloat()
                animatedRegions.add(blendSprites(
                    sprite, lastSprite, f, name
                ))
            }
        }

        animRegions = animatedRegions
    }

    //should be called in Trigger.update
    open fun update() {
        animate = Core.settings.getBool("animateditems")
        if (animate) animIcon.set(animRegions[(Time.globalTime / animDelay).toInt() % n])
    }

    override fun icon(icon: Cicon?): TextureRegion? {
        return if (animate) animIcon else super.icon(icon)
    }
}