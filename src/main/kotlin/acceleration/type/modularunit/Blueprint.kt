package acceleration.type.modularunit

import arc.graphics.Color
import arc.struct.Seq
import mindustry.graphics.Pal

// required for github actions because it errors without it
import acceleration.type.modularunit.ModularUnitProperty

data class Blueprint(
    var name: String = "",
    var modules: Seq<ModularUnitModule> = Seq<ModularUnitModule>(),

    // constant
    /** The amount of cooling this unit has */
    var cooling: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Cooling"
        color = Color.sky
    },
    /** The amount of energy this unit can produce */
    var energyProduction: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Energy Production"
        color = Pal.surge
    },
    /** The amount of energy this unit consumes */
    var energyConsumption: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Energy Consumption"
        color = Pal.surge
    },
    /** The amount of storage in this unit's batteries */
    var energyCapacity: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Energy Capacity"
        color = Pal.surge
    },
    /** The weight of this unit, affects drag & speed */
    var weight: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Weight"
        color = Pal.darkMetal
    },
    /** The complexity of this unit, affects what factories it can be produced in */
    var complexity: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Complexity"
        color = Pal.logicOperations
    },
    /** The volatility of this unit. Affects death explosion strength and chance of death while overheated */
    var volatility: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Volatility"
        color = Pal.lightPyraFlame
    },
    /** The armor toughness of this unit, affects how much armor this unit has */
    var toughness: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Toughness"
        color = Pal.darkMetal
    },

    // active
    /** The current heat level this unit has, can cause explosions */
    var heat: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Heat"
        color = Color.scarlet
    },
    var energy: ModularUnitProperty = ModularUnitProperty().apply {
        this.name = "Energy"
        color = Pal.surge
    }
) {
    var arr = arrayOf(
        cooling, energyProduction, energyConsumption, energyCapacity, weight, complexity, volatility, toughness
    )
    var activeArr = arrayOf(
        heat, energy
    )

    fun update(): Blueprint {
        arr.forEach { it.value = 0f }
        modules.each { it.apply(this) }
        arr.forEach(ModularUnitProperty::update)
        activeArr.forEach(ModularUnitProperty::update)

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