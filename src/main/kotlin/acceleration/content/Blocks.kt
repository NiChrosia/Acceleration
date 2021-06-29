package acceleration.content

import acceleration.world.blocks.defense.*
import acceleration.world.blocks.storage.*
import acceleration.world.blocks.units.ModularUnitFactory
import acceleration.world.blocks.units.Reclaimer
import mindustry.content.*
import mindustry.entities.Units
import mindustry.gen.Sounds
import mindustry.type.*
import mindustry.world.*
import mindustry.world.blocks.defense.turrets.ItemTurret
import mindustry.world.blocks.defense.turrets.Turret
import mindustry.world.meta.BuildVisibility

lateinit var atomCore: Block

lateinit var metaglassWall: Block
lateinit var metaglassWallLarge: Block

lateinit var noneIcon: Block
lateinit var mendIcon: Block
lateinit var overdriveIcon: Block
lateinit var forceIcon: Block

lateinit var configurableProjector: ConfigurableProjector

lateinit var sleet: ItemTurret

lateinit var reclaimer: Reclaimer

lateinit var modularUnitFactory: ModularUnitFactory

fun loadBlocks() {
    atomCore = MenderCoreTurret("atom-core").apply {
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

    // Walls

    metaglassWall = RefractiveWall("metaglass-wall").apply {
        requirements(Category.defense, ItemStack.with(Items.metaglass, 5, Items.titanium, 2))

        size = 1
        health = 360
        absorbLasers = true
    }

    metaglassWallLarge = RefractiveWall("metaglass-wall-large").apply {
        requirements(Category.defense, ItemStack.with(Items.metaglass, 20, Items.titanium, 8))

        size = 2
        health = 360 * 4
        absorbLasers = true
    }

    // Icons

    noneIcon = Block("none-icon").apply {
        buildVisibility = BuildVisibility.hidden
    }


    mendIcon = Block("mend-icon").apply {
        buildVisibility = BuildVisibility.hidden
    }

    overdriveIcon = Block("overdrive-icon").apply {
        buildVisibility = BuildVisibility.hidden
    }

    forceIcon = Block("force-icon").apply {
        buildVisibility = BuildVisibility.hidden
    }

    // Menders

    configurableProjector = ConfigurableProjector("configurable-projector").apply {
        requirements(Category.effect, ItemStack.with(
            Items.lead, 550,
            Items.titanium, 350,
            Items.silicon, 250,
            Items.plastanium, 225,
            Items.surgeAlloy, 175
        ))

        size = 5
    }

    // Turrets

    sleet = ItemTurret("sleet").apply {
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

        consumes.powerCond(350f / 60f, Turret.TurretBuild::isActive)
    }

    // Collectors

    reclaimer = Reclaimer("reclaimer").apply {
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

    // Modular unit factories

    modularUnitFactory = ModularUnitFactory("modular-unit-factory").apply {
        requirements(Category.units, ItemStack.with(

        ))

        size = 3
    }
}