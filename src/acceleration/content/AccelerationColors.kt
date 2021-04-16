package acceleration.content

import arc.graphics.Color
import mindustry.ctype.ContentList

class AccelerationColors : ContentList {
    override fun load() {
        arctifluidColor = Color.valueOf("42E3E3")
        quarkPlasmaColor = Color.valueOf("E0E0E0")

        archeColor = Color.valueOf("dbdbdb")
        archePal1 = Color.valueOf("dbdbdb")
        archePal2 = Color.valueOf("c9c9c9")
        archePal3 = Color.valueOf("b5b5b5")

        overdrive = Color.valueOf("feb380")
    }

    companion object {
        lateinit var arctifluidColor : Color
        lateinit var quarkPlasmaColor : Color

        lateinit var archeColor : Color
        lateinit var archePal1 : Color
        lateinit var archePal2 : Color
        lateinit var archePal3 : Color

        lateinit var overdrive : Color
    }
}