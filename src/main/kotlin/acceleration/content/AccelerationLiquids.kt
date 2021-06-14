package acceleration.content

import mindustry.ctype.ContentList
import mindustry.type.Liquid

class AccelerationLiquids : ContentList {
    override fun load() {
        arctifluid = Liquid("arctifluid").apply {
            heatCapacity = 1.3f
            temperature = 0.05f
            color = AccelerationPal.arctifluid
            effect = AccelerationStatusEffects.arctifreezing
        }

        quarkPlasma = Liquid("quark-plasma").apply {
            temperature = 2.5f
            color = AccelerationPal.quarkPlasma
            effect = AccelerationStatusEffects.liquefying
        }
    }

    companion object {
        lateinit var arctifluid : Liquid
        lateinit var quarkPlasma : Liquid
    }
}