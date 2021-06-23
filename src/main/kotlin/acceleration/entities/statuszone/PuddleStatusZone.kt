package acceleration.entities.statuszone

import arc.graphics.Color
import arc.math.Mathf
import arc.math.geom.Rect
import mindustry.Vars
import mindustry.entities.Units
import mindustry.gen.*
import mindustry.gen.Unit
import mindustry.type.Liquid
import mindustry.type.StatusEffect

open class PuddleStatusZone(
    open val liquid: Liquid,
    color: Color = liquid.color,
    effectLifetime: Float = 40f,

    size: Float = 16f,

    particleChance: Double = 0.2,
    particlePadding: Int = 5,

    damageUnits: Boolean = true,
    damageBuildings: Boolean = true,

    statusEffect: StatusEffect,
    statusEffectLength: Float = 40f
) : StatusZone(
    color = color,
    effectLifetime = effectLifetime,

    size = size,

    particleChance = particleChance,
    particlePadding = particlePadding,

    damageUnits = damageUnits,
    damageBuildings = damageBuildings,

    statusEffect = statusEffect,
    statusEffectLength = statusEffectLength
) {
    override var fieldSettings = listOf("animated-status-zone", "animatedshields")
    override var particleSettings = listOf("status-zone-particles")

    open val puddles = mutableListOf<Puddle>()

    override fun update() {
        super.update()

        puddles.clear()
        puddles.addAll(search().filterIsInstance<Puddle>())

        val entities = searchEntities(puddles)
        damage(entities)
    }

    override fun draw() {
        val range = size - particlePadding

        puddles.forEach { p ->
            fieldEffect.at(p)

            if (drawParticles && Mathf.chance(particleChance)) {
                particleEffect.at(
                    p.x + Mathf.random(-range, range),
                    p.y + Mathf.random(-range, range)
                )
            }
        }
    }

    override fun search(): List<Posc> {
        return Groups.puddle.filter { it.liquid == liquid }
    }

    override fun searchEntities(entities: List<Posc>): List<Posc> {
        val foundEntities = mutableListOf<Posc>()

        entities.forEach {
            Units.nearby(Rect().setCentered(it.x, it.y, size)) { unit ->
                foundEntities.add(unit)
            }

            Vars.indexer.eachBlock(null, it.x, it.y, size, { true }) { building ->
                foundEntities.add(building)
            }
        }

        return foundEntities
    }

    override fun damage(entities: List<Posc>) {
        val units = entities.filterIsInstance<Unit>()
        val buildings = entities.filterIsInstance<Building>()

        if (damageUnits) {
            units.forEach { u ->
                u.apply(statusEffect, statusEffectLength)
            }
        }

        if (damageBuildings) {
            buildings.forEach { b ->
                b.damage(statusEffect.damage)
            }
        }
    }
}