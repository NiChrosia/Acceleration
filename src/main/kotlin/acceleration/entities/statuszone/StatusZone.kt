package acceleration.entities.statuszone

import arc.Core
import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Fill
import arc.graphics.g2d.Lines
import mindustry.entities.Effect
import mindustry.entities.effect.ParticleEffect
import mindustry.gen.Building
import mindustry.gen.Unit
import mindustry.gen.Posc
import mindustry.graphics.Layer
import mindustry.type.StatusEffect

abstract class StatusZone(
    val color: Color,
    val effectLifetime: Float = 40f,

    val size: Float = 16f,

    val particleChance: Double = 0.2,
    val particlePadding: Int = 5,

    val damageUnits: Boolean = true,
    val damageBuildings: Boolean = true,

    val statusEffect: StatusEffect,
    val statusEffectLength: Float = 40f
) {
    abstract var fieldSettings: List<String>
    abstract var particleSettings: List<String>

    abstract fun search(): List<Posc>
    
    /** Search for units and buildings using [entities], which is from [search]. */
    abstract fun searchEntities(entities: List<Posc>): List<Posc>
    
    abstract fun damage(entities: List<Posc>)
    
    abstract fun draw()

    protected val particleEffect: Effect
    protected val shieldEffect: Effect
    protected val lineEffect: Effect

    protected var drawParticles = false
    protected lateinit var fieldEffect: Effect

    open fun update() {
        val animatedField = fieldSettings.map { Core.settings.getBool(it) }.any { it }
        drawParticles = particleSettings.map { Core.settings.getBool(it) }.any { it }

        fieldEffect = if (animatedField) shieldEffect else lineEffect
        
        draw()
    }

    init {
        particleEffect = ParticleEffect().apply {
            colorFrom = color
            colorTo = color
        }

        shieldEffect = Effect(effectLifetime) { e ->
            Draw.color(color)
            Draw.z(Layer.shields)
            Fill.poly(e.x, e.y, 4, size, 45f)
        }

        lineEffect = Effect(effectLifetime) { e ->
            Draw.color(color)
            Draw.z(Layer.shields + 1f)
            Lines.poly(e.x, e.y, 4, size, 45f)
        }
    }
}