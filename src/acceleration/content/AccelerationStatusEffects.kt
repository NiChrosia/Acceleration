package acceleration.content

import arc.util.Log
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
                effect = AccelerationFx.arctifluidFx
                color = AccelerationColors.arctifluidColor
            }
        }

        liquefying = object : StatusEffect("liquefying") {
            init {
                damage = 10f
                effectChance = 1f
                color = AccelerationColors.quarkPlasmaColor
                effect = AccelerationFx.quarkPlasmaFx
            }
        }

        Log.info("Loaded [accent]Acceleration[] [sky]Kotlin[] status effects successfully.")
    }

    companion object {
        lateinit var arctifreezing : StatusEffect
        lateinit var liquefying : StatusEffect
    }
}