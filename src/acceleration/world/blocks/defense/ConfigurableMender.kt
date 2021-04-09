package acceleration.world.blocks.defense

import arc.Core
import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Lines
import arc.graphics.g2d.TextureRegion
import arc.scene.ui.layout.Table
import mindustry.Vars
import mindustry.world.blocks.defense.MendProjector
import mindustry.content.Fx
import arc.math.Mathf
import arc.util.Log
import arc.util.Time
import arc.util.Tmp
import arc.util.io.Reads
import arc.util.io.Writes
import mindustry.gen.Building
import mindustry.graphics.Drawf
import mindustry.graphics.Pal
import mindustry.ui.Styles
import mindustry.world.meta.Stat
import kotlin.math.abs
import kotlin.math.min


open class ConfigurableMender(name: String) : MendProjector(name) {
    open var maxLevels = 5.0

    open var minReload = 20f
    open var maxReload = 350f

    open var minRange = 12f * Vars.tilesize
    open var maxRange = 36f * Vars.tilesize

    open var minHealPercent = 5f
    open var maxHealPercent = 45f

    open var minPowerUse = 0.5f
    open var maxPowerUse = 10f

    var iconRegion: TextureRegion? = null

    override fun setStats() {
        super.setStats()

        stats.remove(Stat.boostEffect)
    }

    /** Set necessary stats upon creation */
    override fun load() {
        super.load()

        configurable = true

        iconRegion = Core.atlas.find("$name-icon")
    }

    override fun icons(): Array<TextureRegion> {
        Log.info("icons loaded")
        Log.info(iconRegion!!)
        Log.info(arrayOf(iconRegion!!))

        return arrayOf(iconRegion!!)
    }

    open inner class ConfigurableMenderBuild : MendProjector.MendBuild() {
        open var level = 0

        private var heat = 0f
        private var charge = 0f
        private var smoothEfficiency = 0f

        private var buildReload = reload
        private var buildRange = range
        private var buildHealPercent = healPercent

        private fun configure(l: Int) {
            level = l
        }

        /** Gets the value between min and max depending on speed */
        private fun calculateSpeed(min: Float, max: Float, speed: Double): Float {
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
        }

        override fun read(read: Reads, revision: Byte) {
            super.read(read, revision)
            level = read.i()
        }

        override fun buildConfiguration(table: Table) {
            for (l in 0 until maxLevels.toInt()) {
                table.button(l.toString(), Styles.clearTogglet) {
                    configure(l)

                    table.clear()
                    buildConfiguration(table)
                }.size(40f).color(Color.lightGray)
            }
        }

        override fun range(): Float {
            return buildRange
        }

        override fun updateTile() {
            val decLevel: Double = (level / maxLevels)

            buildReload = calculateReload(minReload, maxReload, ((decLevel + (1 / maxLevels / 2)) - 0.5) * 2) // Modify value to be more accurate
            buildRange = calculateSpeed(minRange, maxRange, decLevel)
            buildHealPercent = calculateSpeed(minHealPercent, maxHealPercent, decLevel)
            consumes.power(calculateSpeed(minPowerUse, maxPowerUse, decLevel))

            smoothEfficiency = Mathf.lerpDelta(smoothEfficiency, efficiency(), 0.08f)
            heat = Mathf.lerpDelta(heat, if (consValid() || cheating()) 1f else 0f, 0.08f)
            charge += heat * Time.delta

            if (charge >= buildReload) {
                charge = 0f
                Vars.indexer.eachBlock(this, buildRange, { other: Building -> other.damaged() }) { other: Building ->
                    other.heal(other.maxHealth() * (buildHealPercent) / 100f * efficiency())
                    if (efficiency() > 0) Fx.healBlockFull.at(
                        other.x,
                        other.y,
                        other.block.size.toFloat(),
                        baseColor
                    )
                }
            }
        }

        override fun draw() {
            Draw.rect(region, x, y)

            Draw.color(Pal.heal)
            Draw.rect(Core.atlas.find("$name-mode"), x, y)

            Draw.reset()

            val f = 1f - Time.time / 100f % 1f

            if (efficiency() > 0) {
                Draw.color(baseColor)
                Draw.alpha(heat * Mathf.absin(Time.time, 10f, 1f) * 0.5f)
                Draw.rect(topRegion, x, y)
                Draw.alpha(1f)
                Lines.stroke((2f * f + 0.2f) * heat)
                Lines.square(x, y, min(1f + (1f - f) * size * Vars.tilesize / 2f, size * Vars.tilesize / 2f))
            }

            Draw.reset()

            val drawEfficiency = if (efficiency() <= 0.2f) 0.2f else efficiency()

            Draw.color(baseColor)
            Draw.alpha(drawEfficiency)
            Draw.rect(Core.atlas.find("$name-level$level"), x, y)
        }

        override fun drawSelect() {
            Vars.indexer.eachBlock(this, buildRange,
                { true }
            ) { other: Building? ->
                Drawf.selected(
                    other,
                    Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f))
                )
            }

            Drawf.dashCircle(x, y, buildRange, baseColor)
        }
    }
}