package acceleration.world.blocks.defense

import acceleration.content.AccelerationBlocks
import arc.Core
import arc.Events
import arc.graphics.Blending
import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Fill
import arc.graphics.g2d.Lines
import arc.graphics.g2d.TextureRegion
import arc.math.Mathf
import arc.math.geom.Geometry
import arc.math.geom.Intersector
import arc.scene.ui.layout.Table
import arc.struct.ObjectMap
import arc.struct.Seq
import arc.util.Time
import arc.util.Tmp
import arc.util.io.Reads
import arc.util.io.Writes
import mindustry.Vars
import mindustry.content.Fx
import mindustry.content.StatusEffects
import mindustry.entities.Effect
import mindustry.entities.Units
import mindustry.game.EventType
import mindustry.gen.*
import mindustry.graphics.Drawf
import mindustry.graphics.Layer
import mindustry.graphics.Pal
import mindustry.type.Liquid
import mindustry.ui.Bar
import mindustry.ui.Styles
import mindustry.world.Block
import mindustry.world.blocks.ItemSelection
import mindustry.world.blocks.defense.MendProjector
import mindustry.world.consumers.ConsumeLiquidFilter
import mindustry.world.consumers.ConsumeType
import mindustry.world.meta.Stat
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import mindustry.gen.Unit as mUnit

open class ConfigurableProjector(name: String) : MendProjector(name) {
    /** Ticks between each autoadjust. */
    open var adjustTime = 10

    /** The maximum number of levels in the configurable projector. */
    open var maxLevels = 5.0

    /** The numerous minimum and maximum values, these should really be calculated according to a base value */
    var minReload = 50f
    var maxReload = 300f

    var minRange = 12f * Vars.tilesize
    var maxRange = 36f * Vars.tilesize

    var minHealPercent = 5f
    var maxHealPercent = 45f

    var minPowerUse = 0.25f
    var maxPowerUse = 10f

    var minRadius = 101.7f
    var maxRadius = 250f

    var minOverdrive = 150f
    var maxOverdrive = 350f

    var minShieldHealth = 700f
    var maxShieldHealth = 8800f

    var cooldownNormal = 1.75f
    var cooldownLiquid = 1f
    var cooldownBrokenBase = 0.35f

    var paramEntity: ConfigurableProjectorBuild? = null
    val shieldConsumer = { trait: Bullet ->
        paramEntity!!.let {
            if (trait.team !== it.team && trait.type.absorbable && Intersector.isInsideHexagon(
                            it.x, it.y, it.buildRadius * 2f, trait.x(), trait.y()
                    )
            ) {
                trait.absorb()
                Fx.absorb.at(trait)
                paramEntity!!.hit = 1f
                paramEntity!!.buildup += trait.damage() * paramEntity!!.warmup
            }
        }
    }

    /** ObjectMaps for converting between values internally. */

    private val modeMap: ObjectMap<Block, String> = ObjectMap.of(
        AccelerationBlocks.noneIcon, "none",
        AccelerationBlocks.mendIcon, "mend",
        AccelerationBlocks.overdriveIcon, "overdrive",
        AccelerationBlocks.forceIcon, "force"
    )

    private val blockMap: ObjectMap<String, Block> = ObjectMap.of(
        "none", AccelerationBlocks.noneIcon,
        "mend", AccelerationBlocks.mendIcon,
        "overdrive", AccelerationBlocks.overdriveIcon,
        "force", AccelerationBlocks.forceIcon
    )

    private val colorMap: ObjectMap<String, Color> = ObjectMap.of(
        "none", Color.white,
        "mend", Pal.heal,
        "overdrive", Color.valueOf("feb380"),
        "force", Color.valueOf("ffd37f")
    )

    /** Adds two bars; power, and the configurable bar. The configurable bar depends on what the mode is. */
    override fun setBars() {
        super.setBars()

        bars.add(
            "power"
        ) { entity: ConfigurableProjectorBuild ->
            Bar(
                { Core.bundle.format("bar.power") },
                { Pal.powerBar },
                { entity.efficiency() }
            )
        }

        bars.add(
            "configurable"
        ) { entity: ConfigurableProjectorBuild ->
            Bar(
                { when (entity.mode) {
                    "mend" -> Core.bundle.format("bar.mend")
                    "overdrive" -> Core.bundle.format("bar.boost", entity.buildOverdrive)
                    "force" -> Core.bundle.format("stat.shieldhealth")
                    else -> Core.bundle.format("bar.none")
                } },

                { when (entity.mode) {
                    "mend" -> Pal.heal
                    "overdrive" -> Pal.accent
                    "force" -> Pal.accent
                    else -> Color.white
                } },

                { when (entity.mode) {
                    "mend" -> if ((entity.charge / entity.buildReload) == 0f) 1f else entity.charge / entity.buildReload
                    "overdrive" -> entity.buildOverdrive / entity.calculateSpeed(minOverdrive, maxOverdrive, 1.0) /* Actual max speed. */
                    "force" -> (if (entity.broken) 0f else 1f - entity.buildup / entity.buildShieldHealth)
                    else -> 1f
                } }
            )
        }
    }

    override fun setStats() {
        super.setStats()

        stats.remove(Stat.boostEffect)
    }

    /** Set necessary stats upon load */
    init {
        configurable = true
        hasItems = false
        hasPower = true
        sync = true

        consumes.add(
            ConsumeLiquidFilter(
                { liquid: Liquid -> liquid.temperature <= 0.5f && liquid.flammability < 0.1f },
                0.1f
            )
        ).boost().update(false)
    }

    /** Sets the icons (dysfunctional for some reason) */
    override fun icons(): Array<TextureRegion> {
        return arrayOf(Core.atlas.find("$name-icon"))
    }

    open inner class ConfigurableProjectorBuild : MendProjector.MendBuild() {
        var mode: String = "none"
        private var level = 0
        private var autoadjust = false
        private var adjustTicks = 0

        private var heat = 0f
        var charge = 0f
        private var smoothEfficiency = 0f

        /** Slightly better icon visualization */
        open var buildReload = minReload + (maxReload - minReload) / 2
        open var buildRange = minRange + (maxRange - minRange) / 2
        open var buildHealPercent = minHealPercent + (maxHealPercent - minHealPercent) / 2
        open var buildRadius = minRadius + (maxRadius - minRadius) / 2
        open var buildShieldHealth = minShieldHealth + (maxShieldHealth - minShieldHealth) / 2
        open var buildOverdrive = minOverdrive + (maxOverdrive - minOverdrive) / 2
        open var buildPowerUse = 0f

        var broken = true
        var buildup = 0f
        private var radscl: Float = 0f
        var hit: Float = 0f
        var warmup: Float = 0f

        override fun created() {
            Events.on(EventType.Trigger.update::class.java) {
                consumes.remove(ConsumeType.power)

                var powerUse = 0f
                consumes.powerCond<ConfigurableProjectorBuild>(0f) { entity ->
                    if (entity.tile == tile) powerUse = entity.buildPowerUse
                    false
                }

                consumes.powerCond<ConfigurableProjectorBuild>(powerUse) { entity -> entity.tile == tile }
            }
        }

        override fun configure(value: Any?) {
            if (value is String && value.split(" ").size == 3) {
                val valueSeq = Seq(value.split(" ").toTypedArray())

                level = valueSeq[0].toInt()
                autoadjust = valueSeq[1].toBoolean()
                mode = valueSeq[2]
            }

            Call.tileConfig(Vars.player, this, value)
        }

        override fun configured(builder: mUnit?, value: Any?) {
            if (value is String && value.split(" ").size == 3) {
                val valueSeq = Seq(value.split(" ").toTypedArray())

                level = valueSeq[0].toInt()
                autoadjust = valueSeq[1].toBoolean()
                mode = valueSeq[2]
            }
        }

        /** Gets the value between min and max depending on speed */
        fun calculateSpeed(min: Float, max: Float, speed: Double): Float {
            return (min + ((max - min) * speed)).toFloat()
        }

        /** Calculates reload speed from min, max, and speed.
         * The closer speed is to reloadPeak, the higher it is. */
        private fun calculateReload(min: Float, max: Float, speed: Double): Float {
            val diff = abs(speed)

            val outputInverted = min + (max - min) * diff
            val output = (-outputInverted) + max + min

            return output.toFloat()
        }

        override fun write(write: Writes) {
            super.write(write)
            write.i(level)
            write.bool(autoadjust)

            val modeToInt: ObjectMap<String, Int> = ObjectMap.of(
                "none", 0,
                "mend", 1,
                "overdrive", 2,
                "force", 3
            )

            write.i(modeToInt.get(mode))
        }

        override fun read(read: Reads, revision: Byte) {
            super.read(read, revision)
            level = read.i()
            autoadjust = read.bool()

            val intToMode: ObjectMap<Int, String> = ObjectMap.of(
                0, "none",
                1, "mend",
                2, "overdrive",
                3, "force"
            )

            mode = intToMode.get(read.i())
        }

        override fun buildConfiguration(table: Table) {
            table.background(Tex.inventory)

            table.check("AA", autoadjust) { value -> // AA = Auto Adjust
                configure("$level $value $mode")
            }

            for (l in 0 until maxLevels.toInt()) {
                table.button((l + 1).toString(), Styles.clearTogglet) {
                    configure("$l $autoadjust $mode")

                    table.clear()
                    buildConfiguration(table)
                }.size(40f).color(Color.lightGray)
            }

            table.row()

            ItemSelection.buildTable(table, Seq.with(AccelerationBlocks.noneIcon, AccelerationBlocks.mendIcon, AccelerationBlocks.overdriveIcon, AccelerationBlocks.forceIcon), {if (mode == "none") null else blockMap.get(mode)}) { block ->
                if (block != null) {
                    val output = modeMap.get(block)
                    if (output != null) configure("$level $autoadjust $output")
                }
            }
        }

        override fun range(): Float {
            return buildRange
        }

        override fun updateTile() {
            var decLevel: Double = ((level + 1) / maxLevels)
            buildPowerUse = if (mode != "none") calculateSpeed(minPowerUse, maxPowerUse, decLevel) else 0f

            if (mode != "force" && mode != "none") {
                val cons = consumes.get<ConsumeLiquidFilter>(ConsumeType.liquid)
                if (cons.valid(this)) {
                    cons.update(this)
                    if (mode == "overdrive") {
                        decLevel += (cooldownLiquid * (1f + (liquids.current().heatCapacity - 0.4f) * 0.9f)) * 0.05
                    } else if (mode == "mend") {
                        decLevel += (cooldownLiquid * (1f + (liquids.current().heatCapacity - 0.4f) * 0.9f)) * 0.025
                    }
                }
            }

            adjustTicks += Time.delta.toInt()

            if (autoadjust && adjustTicks > adjustTime) {
                adjustTicks = 0

                val powerBal = power.graph.powerBalance * 60

                if (powerBal > buildPowerUse) {
                    if (level.toDouble() < maxLevels - 1) level += 1
                } else if (level > 0) {
                    level -= 1
                }
            }

            buildReload = calculateReload(minReload, maxReload, (decLevel - 0.5) * 2) // Modify value to be more accurate
            buildRange = calculateSpeed(minRange, maxRange, decLevel)
            buildHealPercent = calculateSpeed(minHealPercent, maxHealPercent, decLevel)
            buildRadius = calculateSpeed(minRadius, maxRadius, decLevel)
            buildShieldHealth = calculateSpeed(minShieldHealth, maxShieldHealth, decLevel)
            buildOverdrive = calculateSpeed(minOverdrive, maxOverdrive, decLevel)

            smoothEfficiency = Mathf.lerpDelta(smoothEfficiency, efficiency(), 0.08f)
            heat = Mathf.lerpDelta(heat, if (consValid() || cheating()) 1f else 0f, 0.08f)
            charge += heat * efficiency() * Time.delta

            if (mode == "mend") {
                if (charge >= buildReload) {
                    charge = 0f
                    Vars.indexer.eachBlock(
                        this,
                        buildRange,
                        { other: Building -> other.damaged() }) { other: Building ->
                        other.heal(other.maxHealth() * (buildHealPercent) / 100f * efficiency())
                        if (efficiency() > 0) Fx.healBlockFull.at(
                            other.x,
                            other.y,
                            other.block.size.toFloat(),
                            baseColor
                        )
                    }

                    Units.nearby(team, x - buildRange / 2, y - buildRange / 2, buildRange, buildRange) { u ->
                        u.heal(u.maxHealth * (buildHealPercent / 1000))
                    }
                }
            }
            if (mode == "overdrive" && efficiency() > 0) {

                charge = 0f

                Vars.indexer.eachBlock(this, buildRange,
                    { true }
                ) { other: Building -> other.applyBoost((buildOverdrive / 100f * efficiency()), reload + 1f) }

                Units.nearby(team, x - (buildRadius / 2), y - (buildRadius / 2), buildRadius, buildRadius) { u ->
                    if (Mathf.chance(0.95) && u.team == team) {
                        u.apply(StatusEffects.overdrive, 40f)
                    }
                }
            }
            if (mode == "force") {
                radscl = Mathf.lerpDelta(radscl, if (broken) 0f else warmup, 0.05f)

                if (Mathf.chanceDelta(buildup / buildShieldHealth * 0.1)) {
                    Fx.reactorsmoke.at(x + Mathf.range(Vars.tilesize / 2f), y + Mathf.range(Vars.tilesize / 2f))
                }

                warmup = Mathf.lerpDelta(warmup, efficiency(), 0.1f)

                if (buildup > 0) {
                    var scale: Float = if (!broken) cooldownNormal else cooldownBrokenBase
                    val cons = consumes.get<ConsumeLiquidFilter>(ConsumeType.liquid)
                    if (cons.valid(this)) {
                        cons.update(this)
                        scale *= cooldownLiquid * (1f + (liquids.current().heatCapacity - 0.4f) * 0.9f)
                    }
                    buildup -= delta() * scale
                }

                if (broken && buildup <= 0) {
                    broken = false
                }

                if (buildup >= buildShieldHealth && !broken) {
                    broken = true
                    buildup = buildShieldHealth
                    Fx.shieldBreak.at(x, y, buildRadius, team.color)
                }

                if (hit > 0f) {
                    hit -= 1f / 5f * Time.delta
                }

                val realRadius: Float = buildRadius

                if (realRadius > 0 && !broken) {
                    paramEntity = this
                    Groups.bullet.intersect(
                        x - realRadius,
                        y - realRadius,
                        realRadius * 2f,
                        realRadius * 2f,
                        shieldConsumer
                    )
                }
            }
        }

        override fun draw() {
            Draw.rect(region, x, y)

            Draw.color(colorMap.get(mode))
            Draw.rect(Core.atlas.find("$name-mode"), x, y)

            Draw.reset()

            val f = 1f - Time.time / 100f % 1f

            if (power.graph.powerProduced > 0) {
                Draw.color(colorMap.get(mode))
                Draw.alpha(heat * Mathf.absin(Time.time, 10f, 1f) * 0.5f)
                if (mode != "force") Draw.rect(topRegion, x, y) else {
                    Draw.alpha(buildup / buildShieldHealth * 0.75f)
                    Draw.blend(Blending.additive)
                    Draw.rect(topRegion, x, y)

                    Draw.blend()
                }
                Draw.reset()

                if (mode == "mend") {
                    drawMend(f)
                }
                if (mode == "overdrive") {
                    drawOverdrive(f)
                }
                if (mode == "force") {
                    drawShield()
                }
            }

            Draw.reset()
            Draw.z(Layer.block)

            val drawEfficiency = if (efficiency() <= 0.2f) 0.2f else efficiency()

            Draw.color(colorMap.get(mode))
            Draw.alpha(drawEfficiency)
            Draw.rect(Core.atlas.find("$name-level$level"), x, y)
        }

        override fun onRemoved() {
            if (!broken && buildRadius > 1f) Fx.forceShrink.at(x, y, buildRadius, team.color)
            super.onRemoved()
        }

        private fun drawMend(f: Float) {
            Draw.color(colorMap.get(mode))
            Draw.alpha(1f)
            Lines.stroke((2f * f + 0.2f) * heat)
            Lines.square(x, y, min(1f + (1f - f) * size * Vars.tilesize / 2f, size * Vars.tilesize / 2f))
        }

        private fun drawOverdrive(f: Float) {
            Draw.color(colorMap.get(mode))
            Draw.alpha(1f)
            Lines.stroke((2f * f + 0.1f) * heat)

            val r = max(0f, Mathf.clamp(2f - f * 2f) * size * Vars.tilesize / 2f - f - 0.2f)
            val w = Mathf.clamp(0.5f - f) * size * Vars.tilesize
            Lines.beginLine()
            for (i in 0..3) {
                Lines.linePoint(
                    x + Geometry.d4(i).x * r + Geometry.d4(i).y * w,
                    y + Geometry.d4(i).y * r - Geometry.d4(i).x * w
                )
                if (f < 0.5f) Lines.linePoint(
                    x + Geometry.d4(i).x * r - Geometry.d4(i).y * w,
                    y + Geometry.d4(i).y * r + Geometry.d4(i).x * w
                )
            }
            Lines.endLine(true)
        }

        private fun drawShield() {
            val shieldDraw = Effect(10f) {
                if (!broken) {
                    Draw.z(Layer.shields)
                    Draw.color(team.color, Color.white, Mathf.clamp(hit))
                    if (Core.settings.getBool("animatedshields")) {
                        Fill.poly(x, y, 6, buildRadius)
                    } else {
                        Lines.stroke(1.5f)
                        Draw.alpha(0.09f + Mathf.clamp(0.08f * hit))
                        Fill.poly(x, y, 6, buildRadius)
                        Draw.alpha(1f)
                        Lines.poly(x, y, 6, buildRadius)
                        Draw.reset()
                    }
                }
                Draw.reset()
            }

            shieldDraw.at(Vars.player.x, Vars.player.y) // Draw at player location to avoid sudden disappearance of force field
        }

        override fun drawSelect() {
            if (mode != "force" && mode != "none") {
                Vars.indexer.eachBlock(this, buildRange, { true }) { other: Building? ->
                    Drawf.selected(
                        other,
                        Tmp.c1.set(colorMap.get(mode)).a(Mathf.absin(4f, 1f))
                    )
                }

                Drawf.dashCircle(x, y, buildRange, colorMap.get(mode))
            }
        }
    }
}