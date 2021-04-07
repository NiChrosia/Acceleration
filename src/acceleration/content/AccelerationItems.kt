package acceleration.content

import acceleration.type.item.AnimatedItem
import arc.Events
import arc.graphics.Color
import mindustry.Vars
import mindustry.ctype.ContentList
import mindustry.game.EventType

class AccelerationItems : ContentList {
    override fun load() {
        velosium = object : AnimatedItem("velosium") {
            init {
                color = Color.valueOf("12071f")
                transition = 1
                sprites = 18
            }
        }

        for (i in Vars.content.items()) {
            if (i is AnimatedItem) {
                Events.run(EventType.Trigger.update) { i.update() }
            }
        }
    }

    companion object {
        lateinit var velosium : AnimatedItem
    }
}