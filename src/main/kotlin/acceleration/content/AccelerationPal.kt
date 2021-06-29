package acceleration.content

import arc.graphics.Color
import mindustry.ctype.ContentList

class AccelerationPal : ContentList {
    override fun load() {
        arctifluid = Color.valueOf("42E3E3")
        cryo = Color.valueOf("82cfcf")

        quarkPlasma = Color.valueOf("E0E0E0")

        arche = Color.valueOf("dbdbdb")
        archePal1 = Color.valueOf("dbdbdb")
        archePal2 = Color.valueOf("c9c9c9")
        archePal3 = Color.valueOf("b5b5b5")

        overdrive = Color.valueOf("feb380")
        
        energy = Color.valueOf("03fc7f")
    }

    companion object {
        lateinit var arctifluid: Color
        lateinit var cryo: Color

        lateinit var quarkPlasma: Color

        lateinit var arche: Color
        lateinit var archePal1: Color
        lateinit var archePal2: Color
        lateinit var archePal3: Color

        lateinit var overdrive: Color
        
        lateinit var energy: Color
    }
}