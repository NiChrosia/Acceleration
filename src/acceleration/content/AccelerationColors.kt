package acceleration.content

import arc.graphics.Color
import mindustry.ctype.ContentList

class AccelerationColors : ContentList {
    override fun load() {
        arctifluidColor = Color.valueOf("42E3E3")
        quarkPlasmaColor = Color.valueOf("E0E0E0")

        archaiColor = Color.valueOf("dbdbdb")
        archaiPal1 = Color.valueOf("dbdbdb")
        archaiPal2 = Color.valueOf("c9c9c9")
        archaiPal3 = Color.valueOf("b5b5b5")
    }

    companion object {
        lateinit var arctifluidColor : Color
        lateinit var quarkPlasmaColor : Color

        lateinit var archaiColor : Color
        lateinit var archaiPal1 : Color
        lateinit var archaiPal2 : Color
        lateinit var archaiPal3 : Color
    }
}