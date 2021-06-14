package acceleration.entities.unit

import acceleration.type.modularunit.Blueprint
import mindustry.gen.UnitEntity

open class ModularUnit : UnitEntity() {
    var blueprint: Blueprint = Blueprint()

    companion object {
        fun create(blueprint: Blueprint = Blueprint()): ModularUnit {
            val unit = ModularUnit()
            unit.blueprint = blueprint

            return unit
        }
    }

    override fun speed(): Float {
        return (1f - blueprint.weight.fraction) * 5f
    }

    override fun drag(): Float {
        return blueprint.weight.fraction / 2f
    }
}