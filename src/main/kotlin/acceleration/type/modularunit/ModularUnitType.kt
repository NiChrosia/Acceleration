package acceleration.type.modularunit

import acceleration.entities.unit.ModularUnit
import mindustry.game.Team
import mindustry.gen.Unit
import mindustry.type.UnitType

open class ModularUnitType(name: String) : UnitType(name) {
    init {
        flying = true
    }

    override fun create(team: Team): ModularUnit {
        val unit = constructor.get() as ModularUnit

        unit.setType(this)
        unit.team = team
        unit.ammo = ammoCapacity.toFloat()
        unit.elevation = if (flying) 1f else 0f
        unit.heal()

        return unit
    }

    open fun spawn(team: Team, x: Float, y: Float, blueprint: Blueprint): ModularUnit {
        val out = create(team)
        out.blueprint = blueprint
        out.set(x, y)
        out.add()
        return out
    }

    override fun update(unit: Unit) {

    }
}