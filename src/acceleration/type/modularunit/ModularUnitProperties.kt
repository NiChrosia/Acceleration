package acceleration.type.modularunit

import arc.graphics.Color
import arc.struct.Seq
import mindustry.graphics.Pal

data class ModularUnitProperties(
    var modules: Seq<ModularUnitModule> = Seq<ModularUnitModule>(),
    var cooling: ModularUnitProperty = ModularUnitProperty().apply {
        name = "Cooling"
        color = Color.sky
    },
    var energyProduction: ModularUnitProperty = ModularUnitProperty().apply {
        name = "Energy Production"
        color = Pal.surge
    },
    var energyConsumption: ModularUnitProperty = ModularUnitProperty().apply {
        name = "Energy Consumption"
        color = Pal.surge
    },
    var energyCapacity: ModularUnitProperty = ModularUnitProperty().apply {
        name = "Energy Capacity"
        color = Pal.surge
    },
    var weight: ModularUnitProperty = ModularUnitProperty().apply {
        name = "Weight"
        color = Pal.darkMetal
    },
    var complexity: ModularUnitProperty = ModularUnitProperty().apply {
        name = "Complexity"
        color = Pal.logicOperations
    },
    var volatility: ModularUnitProperty = ModularUnitProperty().apply {
        name = "Volatility"
        color = Pal.lightPyraFlame
    },
    var toughness: ModularUnitProperty = ModularUnitProperty().apply {
        name = "Toughness"
        color = Pal.darkMetal
    }
) {
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

    fun update(): ModularUnitProperties {
        each { it.value = 0f }
        modules.each { it.apply(this) }
        each(ModularUnitProperty::update)

        return this
    }

    fun add(module: ModularUnitModule): ModularUnitProperties {
        this.modules.add(module)

        return this
    }

    /** Get the specified module by its internal name. Returns null if not found. */
    fun get(moduleName: String): ModularUnitModule? {
        var output: ModularUnitModule? = null

        modules.each {
            if (it.internalName == moduleName) output = it
        }

        return output
    }
}