package acceleration.content

import mindustry.ctype.*
import mindustry.type.*

import mindustry.world.*

import mindustry.content.*

import acceleration.world.blocks.storage.*
import acceleration.world.blocks.defense.*
import acceleration.world.blocks.defense.turrets.LogicOverlayItemTurret
import arc.util.Log
import mindustry.gen.Sounds
import mindustry.world.blocks.defense.turrets.ItemTurret
import mindustry.world.blocks.defense.turrets.PowerTurret

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
                itemCapacity = 32000
                unitCapModifier = 48
                researchCostMultiplier = 0.25f
                unitType = AccelerationUnits.quark
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

        // Turrets

        transistor = object : PowerTurret("transistor") {
            init {
                requirements(Category.turret, ItemStack.with())

                rotateSpeed = 8f
                health = 360
                range = 110f
                powerUse = 0.25f
                reloadTime = 30f
                size = 1
                spread = 6f
                shootType = AccelerationBullets.standardLaserBolt
                shootSound = Sounds.lasershoot
            }
        }

        gate = object : LogicOverlayItemTurret("gate") {
            init {
                requirements(Category.turret, ItemStack.with())

                rotateSpeed = 8f
                health = 1150
                range = 270f
                reloadTime = 85f
                size = 2
                recoilAmount = 4f
                inaccuracy = 1.5f
                shootSound = Sounds.artillery
                targetAir = false

                ammo(
                    Items.graphite, AccelerationBullets.railArtilleryDense,
                    Items.silicon, AccelerationBullets.railArtilleryHoming,
                    Items.pyratite, AccelerationBullets.railArtilleryIncendiary,
                    Items.metaglass, AccelerationBullets.railArtilleryGlass
                )
            }
        }

        capacitor = object : ItemTurret("capacitor") {
            init {
                requirements(Category.turret, ItemStack.with())

                rotateSpeed = 12f
                health = 1650
                range = 210f
                reloadTime = 24f
                size = 3

                ammo(
                    Items.sporePod, AccelerationBullets.sporeStatusZone
                )
            }
        }
    }

    companion object {
        lateinit var atomCore: Block

        lateinit var metaglassWall: Block
        lateinit var metaglassWallLarge: Block

        lateinit var transistor : PowerTurret
        lateinit var gate : LogicOverlayItemTurret
        lateinit var capacitor : ItemTurret
    }
}