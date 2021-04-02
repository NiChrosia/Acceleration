package acceleration.content

import mindustry.ctype.*
import mindustry.type.*

import mindustry.world.*

import mindustry.content.*

import acceleration.world.blocks.storage.*
import acceleration.world.blocks.defense.*

class AccelerationBlocks : ContentList {
    override fun load() {
        // Cores

        atomCore = object : MenderCoreTurret("atom-core") {
            init {
                requirements(Category.effect, ItemStack.with(
                    Items.copper, 24000,
                    Items.lead, 24000,
                    Items.thorium, 12000,
                    Items.silicon, 12000,
                    Items.surgeAlloy, 8000
                ))

                size = 6
                health = 48000
            }
        }

        // Walls

        metaglassWall = object : RefractiveWall("metaglass-wall") {
            init {
                requirements(Category.defense, ItemStack.with(Items.metaglass, 5, Items.titanium, 2))

                size = 1
                health = 360
                absorbLasers = true
            }
        }

        metaglassWallLarge = object : RefractiveWall("metaglass-wall-large") {
            init {
                requirements(Category.defense, ItemStack.with(Items.metaglass, 20, Items.titanium, 8))

                size = 2
                health = 360 * 4
                absorbLasers = true
            }
        }
    }

    companion object {
        lateinit var metaglassWall: Block
        lateinit var metaglassWallLarge: Block
        lateinit var atomCore: Block
    }
}