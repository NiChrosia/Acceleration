package acceleration.type.modularunit

import acceleration.Acceleration
import arc.Core
import arc.graphics.Color
import arc.graphics.g2d.TextureRegion
import mindustry.type.ItemStack

data class ModularUnitModule(
    var name: String = "",
    var description: String = "",
    var color: Color = Color.white,
    var requirements: () -> Array<ItemStack> = { ItemStack.with() },
    var internalName: String = "error",
    var icon: TextureRegion = Core.atlas.find("error"),
    var modifiers: ModularUnitProperties = ModularUnitProperties(),
    var level: Int = 0,
    var max: Int = 1
) {
    init {
        icon = Core.atlas.find("${Acceleration.modName}-module-$internalName")
    }

    fun apply(other: ModularUnitProperties): ModularUnitProperties {
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