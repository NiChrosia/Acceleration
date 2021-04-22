package acceleration.content

import acceleration.world.blocks.defense.*
import acceleration.world.blocks.storage.*
import acceleration.world.blocks.units.Reclaimer
import mindustry.content.*
import mindustry.ctype.*
import mindustry.entities.Units
import mindustry.gen.Sounds
import mindustry.type.*
import mindustry.world.*
import mindustry.world.blocks.defense.turrets.ItemTurret
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
                unitType = AccelerationUnitTypes.quark
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

        sleet = object : ItemTurret("sleet") {
            init {
                requirements(Category.turret, ItemStack.with())

                ammoUseEffect = Fx.casing3Double

                size = 3
                health = 2250
                range = 425f

                reloadTime = 350f
                ammoPerShot = 6
                maxAmmo = 60

                coolantMultiplier = 0.8f
                coolantUsage = 2f

                ammo(
                    AccelerationItems.voltaicVelosium, AccelerationBullets.voltaicRail
                )

                shootCone = 2f
                shootSound = Sounds.railgun
                unitSort = Units.Sortf { u, _, _ -> -u.maxHealth }

                recoilAmount = 8f

                consumes.powerCond(350f / 60f, TurretBuild::isActive)
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

                range = 200f
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

        lateinit var sleet : ItemTurret

        lateinit var reclaimer : Reclaimer
    }
}