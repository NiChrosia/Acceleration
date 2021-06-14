package acceleration.type.modularunit

import acceleration.Acceleration
import acceleration.entities.unit.ModularUnit
import arc.func.Prov
import mindustry.game.Team
import mindustry.gen.EntityMapping
import mindustry.type.UnitType

open class ModularUnitType(name: String) : UnitType(name) {
    @Suppress("unchecked_cast")
    var unitConstructor: Prov<ModularUnit> = EntityMapping.map("${Acceleration.modName}-$name") as Prov<ModularUnit>

    override fun create(team: Team?): ModularUnit {
        val unit = unitConstructor.get()
        unit.team = team
        unit.setType(this)
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
}