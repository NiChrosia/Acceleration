package acceleration.type.modularunit

import arc.graphics.Color
import arc.struct.Seq
import mindustry.graphics.Pal

data class Blueprint(
    var name: String = "",
    var modules: Seq<ModularUnitModule> = Seq<ModularUnitModule>(),
    var cooling: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Cooling"
        color = Color.sky
    },
    var energyProduction: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Energy Production"
        color = Pal.surge
    },
    var energyConsumption: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Energy Consumption"
        color = Pal.surge
    },
    var energyCapacity: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Energy Capacity"
        color = Pal.surge
    },
    var weight: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Weight"
        color = Pal.darkMetal
    },
    var complexity: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Complexity"
        color = Pal.logicOperations
    },
    var volatility: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Volatility"
        color = Pal.lightPyraFlame
    },
    var toughness: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Toughness"
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
    fun each(iterator: (ModularUnitProperty) -> Unit): Blueprint {
        arr.forEach(iterator)

        return this
    }

    /** Iterate over all properties that are `ModularUnitProperty`s, with an index. */
    fun eachIndexed(iterator: (Int, ModularUnitProperty) -> Unit): Blueprint {
        arr.forEachIndexed(iterator)

        return this
    }

    fun update(): Blueprint {
        each { it.value = 0f }
        modules.each { it.apply(this) }
        each(ModularUnitProperty::update)

        return this
    }

    fun add(module: ModularUnitModule): Blueprint {
        this.modules.add(module)

        return this
    }

    /** Get the specified module by its internal name. Returns null if not found. */
    fun getModule(moduleName: String): ModularUnitModule? {
        val output = modules.copy().filter { it.internalName == moduleName }

        return if (!output.isEmpty) output.first() else null
    }

    fun getModule(module: ModularUnitModule): ModularUnitModule? {
        return getModule(module.internalName)
    }

    fun hasModule(module: ModularUnitModule): Boolean {
        return !modules.copy().filter { it.internalName == module.internalName }.isEmpty
    }
}