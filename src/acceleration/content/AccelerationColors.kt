package acceleration.content

import arc.graphics.Color
import mindustry.ctype.ContentList

class AccelerationColors : ContentList {
    override fun load() {
        arctifluidColor = Color.valueOf("42E3E3")
        quarkPlasmaColor = Color.valueOf("E0E0E0")
    }

    companion object {
        lateinit var arctifluidColor : Color
        lateinit var quarkPlasmaColor : Color
    }
}