package acceleration.content

import acceleration.type.item.AnimatedItem
import arc.Events
import arc.graphics.Color
import mindustry.Vars
import mindustry.ctype.ContentList
import mindustry.game.EventType
import mindustry.type.Item

class AccelerationItems : ContentList {
    override fun load() {
        velosium = object : Item("velosium") {
            init {
                color = Color.valueOf("12071f")
                charge = 0.45f
            }
        }

        arcaneVelosium = object : Item("arcane-velosium") {
            init {
                color = Color.valueOf("270330")
                charge = 0.25f
            }
        }

        electricVelosium = object : Item("electric-velosium") {
            init {
                color = Color.valueOf("b8b80d")
                charge = 1.25f
            }
        }

        cryocatalyst = object : AnimatedItem("cryocatalyst") {
            init {
                color = Color.sky
                transition = 3
                sprites = 6
            }
        }

        for (i in Vars.content.items()) {
            if (i is AnimatedItem) {
                Events.run(EventType.Trigger.update) { i.update() }
            }
        }
    }

    companion object {
        lateinit var velosium : Item
        lateinit var arcaneVelosium : Item
        lateinit var electricVelosium : Item

        lateinit var cryocatalyst : AnimatedItem
    }
}