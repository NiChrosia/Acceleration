package acceleration.content

import mindustry.ctype.*
import mindustry.type.*

import mindustry.world.blocks.defense.*

import mindustry.content.*

class AccelerationBlocks : ContentList {

    override fun load() {
        
        metaglassWall = object : Wall("metaglass-wall") {
            init {
                requirements(Category.defense, with(Items.metaglass, 5, Items.titanium, 2))

                size = 1
                health = 360
            }
        }
    }

    companion object {
        lateinit var metaglassWall: Block
    }
}