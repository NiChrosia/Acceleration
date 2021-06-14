package acceleration.entities.unit

import acceleration.type.modularunit.Blueprint
import arc.math.Mathf
import mindustry.entities.Damage
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

    open fun updateHeat() {
        blueprint.heat.value = Mathf.clamp(blueprint.heat.value - blueprint.cooling.value, 0f, blueprint.heat.max)
    }

    override fun update() {
        super.update()

        updateHeat()

        if (blueprint.heat.fraction > 0.6f) {
            if (Mathf.chance(blueprint.volatility.fraction / 1000.0)) {
                Damage.damage(team, x, y, blueprint.volatility.fraction * 200f, blueprint.volatility.fraction * 100f)
                type.deathExplosionEffect.at(this)
                destroy()
            }
        }
    }
}