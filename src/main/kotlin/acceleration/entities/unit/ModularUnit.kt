package acceleration.entities.unit

import acceleration.Acceleration
import acceleration.content.AccelerationUnitTypes
import acceleration.type.modularunit.Blueprint
import arc.math.Mathf
import arc.util.io.Reads
import arc.util.io.Writes
import mindustry.entities.Damage
import mindustry.gen.UnitEntity
import acceleration.type.modularunit.MUModule

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

        blueprint.update()

        if (blueprint.heat.fraction > 0.6f) {
            if (Mathf.chance(blueprint.volatility.fraction / 1000.0)) {
                Damage.damage(team, x, y, blueprint.volatility.fraction * 200f, blueprint.volatility.fraction * 100f)
                type.deathExplosionEffect.at(this)
                destroy()
            }
        }
    }

    override fun write(write: Writes) {
        super.write(write)

        fun MUModule.serialize(): String {
            return "$internalName,$level"
        }

        write.bool(blueprint.modules.isNotEmpty())
        write.str(blueprint.modules.joinToString(", ", transform = MUModule::serialize))
    }

    override fun read(read: Reads) {
        super.read(read)

        val arrayEmpty = read.bool()
        val serialized = read.str()

        if (arrayEmpty) {
            serialized.split(", ").forEach {
                val (name, level) = it.split(",")

                Acceleration.MUModules.get(name)?.let { existingModule ->
                    blueprint.modules.add(existingModule.copy(name = name, level = level.toInt()))
                }
            }
        }
    }

    override fun classId() = AccelerationUnitTypes.classID(this::class)
    override fun toString() = "ModularUnit#$id"
}