package acceleration.entities.abilities

import acceleration.math.geom.Geometrym
import arc.graphics.Color
import arc.math.Mathf
import arc.math.geom.Vec2
import arc.util.Time
import mindustry.entities.Lightning
import mindustry.entities.abilities.Ability
import mindustry.gen.Sounds
import mindustry.gen.Unit

class LightningFieldAbility(
        private val lightningPoints: Int,
        private val lightningDamage: Float,
        private val baseHitSizeOffset: Float,
        private val hitSizeMultiplier: Float,
        private val color: Color
    ) : Ability() {
    private var hitSizeOffset = baseHitSizeOffset
    private val lightningLengthDivisor = 2.15f

    override fun update(unit: Unit?) {
        super.update(unit)

        if (unit != null) {
            hitSizeOffset = baseHitSizeOffset * Mathf.absin(35f, hitSizeMultiplier)

            circleLightning(((Time.time % 1) * 360).toInt(), unit)
        }
    }

    private fun circleLightning(offset: Int, unit: Unit) {
        val pointSeq = Geometrym.normalPoly(lightningPoints, unit.hitSize + hitSizeOffset, offset)

        pointSeq.onEachIndexed { index, vec ->
            val next: Vec2? = try {
                pointSeq.get(index + 1)
            } catch(e: Exception) {
                pointSeq.get(0)
            }

            next?.let { nextVector ->
                Lightning.create(
                    unit.team, color, lightningDamage,

                    unit.x + vec.x,
                    unit.y + vec.y,

                    vec.angleTo(nextVector.x, nextVector.y),
                    (vec.dst(nextVector) / lightningLengthDivisor).toInt()
                )

                Sounds.spark.at(unit.x, unit.y, Mathf.random(0.9f, 1.1f), 0.5f)
            }
        }
    }
}