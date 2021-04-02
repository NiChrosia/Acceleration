package acceleration.content

import arc.graphics.Color
import arc.util.Log
import mindustry.ctype.ContentList

class AccelerationColors : ContentList {
    override fun load() {
        arctifluidColor = Color.valueOf("42E3E3")
        quarkPlasmaColor = Color.valueOf("E0E0E0")

        Log.info("Loaded [accent]Acceleration[] [sky]Kotlin[] colors successfully.")
    }

    companion object {
        lateinit var arctifluidColor : Color
        lateinit var quarkPlasmaColor : Color
    }
}