package acceleration.type.modularunit

import acceleration.Acceleration
import arc.Core
import arc.graphics.Color
import arc.graphics.g2d.TextureRegion
import mindustry.type.ItemStack

data class MUModule(
    /** The formatted name of the module. */
    var name: String = "",

    /** The formatted description of the module. */
    var description: String = "",

    /** The color displayed on the name of the module. */
    var color: Color = Color.white,

    /** The requirements to produce this module, multiplied by [costMultiplier] per each increase in level. */
    var requirements: () -> Array<ItemStack> = { ItemStack.with() },

    /** The amount the requirements are multiplied by each level. Defaults to 1. */
    var costMultiplier: Int = 1,

    /** The internal name of the module, used for serialization. */
    var internalName: String = "error",

    /** The icon to display in the modular unit factory module list when creating a [ModularUnitType]. */
    var icon: TextureRegion = Core.atlas.find("error"),

    /** The modifiers of the properties of the modular unit, typically includes [Blueprint.weight] & [Blueprint.energyConsumption]. */
    var modifiers: Blueprint = Blueprint(),

    /** The current level of this module. Affects requirement cost. */
    var level: Int = 0,

    /** The maximum level of this module. */
    var max: Int = 1
) {
    init {
        icon = Core.atlas.find("${Acceleration.modName}-module-$internalName")
    }

    /** Applies this module to a blueprint, updating its modifiers. */
    fun apply(other: Blueprint): Blueprint {
        if (level > max) return modifiers

        for (unused in 1..level) {
            other.cooling.value += modifiers.cooling.value
            other.energyProduction.value += modifiers.energyProduction.value
            other.energyConsumption.value += modifiers.energyConsumption.value
            other.energyCapacity.value += modifiers.energyCapacity.value
            other.weight.value += modifiers.weight.value
            other.complexity.value += modifiers.complexity.value
            other.volatility.value += modifiers.volatility.value
            other.toughness.value += modifiers.toughness.value
        }

        return other
    }
}