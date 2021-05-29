package acceleration.type.modularunit

import arc.graphics.Color
import mindustry.graphics.Pal

data class ModularUnitProperties(val unused: Nothing? = null) {
    val cooling = ModularUnitProperty().apply {
        name = "Cooling"
        color = Color.sky
    }

    val energyProduction = ModularUnitProperty().apply {
        name = "Energy Production"
        color = Pal.surge
    }

    val energyConsumption = ModularUnitProperty().apply {
        name = "Energy Consumption"
        color = Pal.surge
    }

    val energyCapacity = ModularUnitProperty().apply {
        name = "Energy Capacity"
        color = Pal.surge
    }

    val weight = ModularUnitProperty().apply {
        name = "Weight"
        color = Pal.darkMetal
    }

    val complexity = ModularUnitProperty().apply {
        name = "Complexity"
        color = Pal.logicOperations
    }

    val volatility = ModularUnitProperty().apply {
        name = "Volatility"
        color = Pal.lightPyraFlame
    }

    val toughness = ModularUnitProperty().apply {
        name = "Toughness"
        color = Pal.darkMetal
    }

    private var arr = arrayOf(
        cooling, energyProduction, energyConsumption, energyCapacity, weight, complexity, volatility, toughness
    )

    operator fun get(index: Int): ModularUnitProperty {
        return arr[index]
    }

    /** Iterate over all properties that are `ModularUnitProperty`s */
    fun each(iterator: (ModularUnitProperty) -> Unit): ModularUnitProperties {
        arr.forEach(iterator)

        return this
    }

    /** Iterate over all properties that are `ModularUnitProperty`s, with an index. */
    fun eachIndexed(iterator: (Int, ModularUnitProperty) -> Unit): ModularUnitProperties {
        arr.forEachIndexed(iterator)

        return this
    }
}