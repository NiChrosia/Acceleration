package acceleration.content

import mindustry.ctype.*
import mindustry.type.*

import mindustry.world.*

import mindustry.content.*

import acceleration.world.blocks.storage.*
import acceleration.world.blocks.defense.*
import acceleration.world.blocks.defense.turrets.LogicOverlayItemTurret
import acceleration.world.blocks.units.Reclaimer
import mindustry.gen.Sounds
import mindustry.world.blocks.defense.turrets.ItemTurret
import mindustry.world.blocks.defense.turrets.PowerTurret
import mindustry.world.meta.BuildVisibility

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

        // Icons

        noneIcon = object : Block("none-icon") {
            init {
                buildVisibility = BuildVisibility.hidden
            }
        }


        mendIcon = object : Block("mend-icon") {
            init {
                buildVisibility = BuildVisibility.hidden
            }
        }

        overdriveIcon = object : Block("overdrive-icon") {
            init {
                buildVisibility = BuildVisibility.hidden
            }
        }

        forceIcon = object : Block("force-icon") {
            init {
                buildVisibility = BuildVisibility.hidden
            }
        }

        // Menders

        configurableProjector = object : ConfigurableProjector("configurable-projector") {
            init {
                requirements(Category.effect, ItemStack.with(
                    Items.lead, 550,
                    Items.titanium, 350,
                    Items.silicon, 250,
                    Items.plastanium, 225,
                    Items.surgeAlloy, 175
                ))

                size = 5
            }
        }

        // Turrets

        transistor = object : PowerTurret("transistor") {
            init {
                requirements(Category.turret, ItemStack.with(Items.copper, 50, Items.lead, 25, Items.silicon, 15))

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
                requirements(Category.turret, ItemStack.with(Items.copper, 75, Items.lead, 50, Items.silicon, 45, Items.metaglass, 25))

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
                requirements(Category.turret, ItemStack.with(Items.lead, 150, Items.titanium, 150, Items.thorium, 120, Items.plastanium, 75))

                rotateSpeed = 12f
                health = 1650
                range = 210f
                reloadTime = 24f
                size = 3

                ammo(
                    Items.sporePod, AccelerationBullets.sporeStatusZone,
                    Items.pyratite, AccelerationBullets.pyraStatusZone,
                    Items.thorium, AccelerationBullets.thoriumStatusZone,
                    Items.surgeAlloy, AccelerationBullets.surgeStatusZone
                )
            }
        }

        // Collectors

        reclaimer = object : Reclaimer("reclaimer") {
            init {
                requirements(Category.effect, ItemStack.with(
                    Items.copper, 200,
                    Items.lead, 200,
                    Items.graphite, 150,
                    Items.titanium, 200
                ))

                tier = 3f
                health = 850
                size = 3
                itemCapacity = 400
            }
        }
    }

    companion object {
        lateinit var atomCore: Block

        lateinit var metaglassWall: Block
        lateinit var metaglassWallLarge: Block

        lateinit var noneIcon : Block
        lateinit var mendIcon : Block
        lateinit var overdriveIcon : Block
        lateinit var forceIcon : Block

        lateinit var configurableProjector : ConfigurableProjector

        lateinit var transistor : PowerTurret
        lateinit var gate : LogicOverlayItemTurret
        lateinit var capacitor : ItemTurret

        lateinit var reclaimer : Reclaimer
    }
}