package acceleration.entities.statuszone

import arc.graphics.Color
import arc.math.Mathf
import arc.math.geom.Rect
import mindustry.Vars
import mindustry.entities.Units
import mindustry.entities.bullet.BulletType
import mindustry.gen.*
import mindustry.gen.Unit
import mindustry.type.StatusEffect

open class BulletStatusZone(
    open val bullet: BulletType,
    color: Color,
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

    open val bullets = mutableListOf<Bullet>()

    override fun update() {
        super.update()

        bullets.clear()
        bullets.addAll(search().filterIsInstance<Bullet>())

        val entities = searchEntities(bullets)
        damage(entities)
    }

    override fun draw() {
        val range = size - particlePadding

        bullets.forEach { b ->
            fieldEffect.at(b)

            if (drawParticles && Mathf.chance(particleChance)) {
                particleEffect.at(
                    b.x + Mathf.random(-range, range),
                    b.y + Mathf.random(-range, range)
                )
            }
        }
    }

    override fun search(): List<Posc> {
        return Groups.bullet.filter { it.type == bullet }
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