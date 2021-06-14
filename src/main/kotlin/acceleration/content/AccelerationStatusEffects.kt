package acceleration.content

import mindustry.content.StatusEffects
import mindustry.ctype.ContentList
import mindustry.type.StatusEffect

class AccelerationStatusEffects : ContentList {
    override fun load() {
        arctifreezing = object : StatusEffect("arctifreezing") {
            init {
                speedMultiplier = 0.75f
                reloadMultiplier = 0.8f
                damage = 1f
                effectChance = 0.85f
                transitionDamage = 24f

                effect = AccelerationFx.arctifluidFx
                color = AccelerationPal.arctifluid
            }

            override fun init() {
                super.init()

                affinity(StatusEffects.blasted) { unit, result, time ->
                    unit.damagePierce(transitionDamage)
                    result.set(StatusEffects.blasted, time)
                }

                opposite(StatusEffects.burning)
                opposite(StatusEffects.melting)
                opposite(liquefying)
            }
        }

        StatusEffects.burning.opposites.add(arctifreezing)
        StatusEffects.melting.opposites.add(arctifreezing)

        liquefying = object : StatusEffect("liquefying") {
            init {
                damage = 10f
                effectChance = 1f
                color = AccelerationPal.quarkPlasma
                effect = AccelerationFx.quarkPlasmaFx
            }

            override fun init() {
                super.init()

                affinity(StatusEffects.tarred) { unit, result, time ->
                    unit.damagePierce(transitionDamage)
                    result.set(StatusEffects.tarred, time)
                }

                opposite(arctifreezing)
            }
        }

        StatusEffects.tarred.affinities.add(liquefying)

        overloaded = object : StatusEffect("overloaded") {
            init {
                reloadMultiplier = 0.3f
                speedMultiplier = 0.6f
            }
        }
    }

    companion object {
        lateinit var arctifreezing : StatusEffect
        lateinit var liquefying : StatusEffect

        lateinit var overloaded : StatusEffect
    }
}