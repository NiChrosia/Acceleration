package acceleration.content

import arc.util.Log
import mindustry.content.StatusEffects
import mindustry.ctype.ContentList
import mindustry.type.StatusEffect

class AccelerationStatusEffects : ContentList {
    override fun load() {
        arctifreezing = object : StatusEffect("arctifreezing") {
            init {
                speedMultiplier = 0.75f
                reloadMultiplier = 0.8f
                damage = 2.5f
                healthMultiplier = 1.2f
                effectChance = 0.85f
                transitionDamage = 24f

                effect = AccelerationFx.arctifluidFx
                color = AccelerationColors.arctifluidColor
            }

            override fun init() {
                super.init()

                affinity(StatusEffects.blasted) { unit, time, newTime, result ->
                    unit.damagePierce(transitionDamage)
                    result.set(StatusEffects.blasted, newTime + time)
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
                color = AccelerationColors.quarkPlasmaColor
                effect = AccelerationFx.quarkPlasmaFx
            }

            override fun init() {
                super.init()

                affinity(StatusEffects.tarred
                ) { _, time, newTime, result -> result.set(StatusEffects.tarred, newTime + time) }

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