package acceleration.type.modularunit

import arc.graphics.Color
import mindustry.graphics.Pal

data class Blueprint(
    var name: String = "",
    var modules: MutableList<MUModule> = mutableListOf(),

    // constant
    /** The amount of cooling this unit has */
    var cooling: ModuleProperty = ModuleProperty().apply {
        this.name = "Cooling"
        color = Color.sky
    },
    /** The amount of energy this unit can produce */
    var energyProduction: ModuleProperty = ModuleProperty().apply {
        this.name = "Energy Production"
        color = Pal.surge
    },
    /** The amount of energy this unit consumes */
    var energyConsumption: ModuleProperty = ModuleProperty().apply {
        this.name = "Energy Consumption"
        color = Pal.surge
    },
    /** The amount of storage in this unit's batteries */
    var energyCapacity: ModuleProperty = ModuleProperty().apply {
        this.name = "Energy Capacity"
        color = Pal.surge
    },
    /** The weight of this unit, affects drag & speed */
    var weight: ModuleProperty = ModuleProperty().apply {
        this.name = "Weight"
        color = Pal.darkMetal
    },
    /** The complexity of this unit, affects what factories it can be produced in */
    var complexity: ModuleProperty = ModuleProperty().apply {
        this.name = "Complexity"
        color = Pal.logicOperations
    },
    /** The volatility of this unit. Affects death explosion strength and chance of death while overheated */
    var volatility: ModuleProperty = ModuleProperty().apply {
        this.name = "Volatility"
        color = Pal.lightPyraFlame
    },
    /** The armor toughness of this unit, affects how much armor this unit has */
    var toughness: ModuleProperty = ModuleProperty().apply {
        this.name = "Toughness"
        color = Pal.darkMetal
    },

    // active
    /** The current heat level this unit has, can cause explosions */
    var heat: ModuleProperty = ModuleProperty().apply {
        this.name = "Heat"
        color = Color.scarlet
    },
    var energy: ModuleProperty = ModuleProperty().apply {
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
        modules.forEach { it.apply(this) }
        arr.forEach(ModuleProperty::update)
        activeArr.forEach(ModuleProperty::update)

        return this
    }

    fun add(module: MUModule): Blueprint {
        this.modules.add(module)

        return this
    }

    /** Get the specified module by its internal name. Returns null if not found. */
    fun getModule(moduleName: String): MUModule? {
        val output = modules.filter { it.internalName == moduleName }

        return if (output.isNotEmpty()) output.first() else null
    }

    fun getModule(module: MUModule): MUModule? {
        return getModule(module.internalName)
    }

    fun hasModule(module: MUModule): Boolean {
        return !modules.none { it.internalName == module.internalName }
    }
}