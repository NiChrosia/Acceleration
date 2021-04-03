package acceleration.content

import arc.util.Log
import mindustry.ctype.ContentList
import mindustry.type.Liquid

class AccelerationLiquids : ContentList {
    override fun load() {
        arctifluid = object : Liquid("arctifluid") {
            init {
                heatCapacity = 1.3f
                temperature = 0.05f
                color = AccelerationColors.arctifluidColor
                effect = AccelerationStatusEffects.arctifreezing
            }
        }

        quarkPlasma = object : Liquid("quark-plasma") {
            init {
                temperature = 2.5f
                color = AccelerationColors.quarkPlasmaColor
                effect = AccelerationStatusEffects.liquefying
            }
        }
    }

    companion object {
        lateinit var arctifluid : Liquid
        lateinit var quarkPlasma : Liquid
    }
}