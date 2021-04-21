package acceleration.type.item

import acceleration.graphics.Drawm
import arc.Core
import arc.graphics.Color
import mindustry.type.Item
import arc.graphics.g2d.TextureRegion
import arc.util.Time
import mindustry.ui.Cicon
import arc.graphics.Texture
import arc.graphics.Pixmap
import arc.struct.Seq

/** An animated item. Requires sprites for all the frames, and supports blending between sprites
Credits to sk7725 for the idea and majority of code */
open class AnimatedItem(name: String) : Item(name) {
    private val animIcon = TextureRegion()
    private var animRegions: Seq<TextureRegion> = Seq()

    private var animDelay = 3f
    private var animate = true

    /** Number of initial sprites. */
    var sprites = 10

    /** # of transition frames inserted between two sprites. */
    var transition = 0

    // set in load()
    private var n = 0

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
                animatedRegions.add(
                    Drawm.blendSprites(
                    sprite, lastSprite, f, name
                ))
            }
        }

        animRegions = animatedRegions
    }

    // Needs to be called in Trigger.update for correct performance
    open fun update() {
        animate = Core.settings.getBool("animateditems")
        if (animate) animIcon.set(animRegions[(Time.globalTime / animDelay).toInt() % n])
    }

    override fun icon(icon: Cicon?): TextureRegion? {
        return if (animate) animIcon else super.icon(icon)
    }
}