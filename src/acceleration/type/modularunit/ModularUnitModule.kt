package acceleration.type.modularunit

import acceleration.Acceleration
import arc.Core
import arc.graphics.Color
import arc.graphics.g2d.TextureRegion
import mindustry.type.ItemStack

class ModularUnitModule {
    var name: String = ""
    var description: String = ""
    var color: Color = Color.white
    var requirements: () -> Array<ItemStack> = { ItemStack.with() }
    var internalName: String = "error"
      set(name) {
          field = name

          icon = Core.atlas.find("${Acceleration.modName}-module-$internalName")
      }

    var icon: TextureRegion = Core.atlas.find("${Acceleration.modName}-module-$internalName")
    var modifiers: ModularUnitProperties = ModularUnitProperties()

    var level: Int = 0
    var max: Int = 1

    fun apply(other: ModularUnitProperties) {
        if (level >= max) return

        level++

        other.cooling.value += this.modifiers.cooling.value
        other.energyProduction.value += this.modifiers.energyProduction.value
        other.energyConsumption.value += this.modifiers.energyConsumption.value
        other.energyCapacity.value += this.modifiers.energyCapacity.value
        other.weight.value += this.modifiers.weight.value
        other.complexity.value += this.modifiers.complexity.value
        other.volatility.value += this.modifiers.volatility.value
        other.toughness.value += this.modifiers.toughness.value
    }
}