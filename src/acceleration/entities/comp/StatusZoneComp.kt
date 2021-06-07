package acceleration.entities.comp

import arc.Core
import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Fill
import arc.graphics.g2d.Lines
import arc.math.Mathf
import arc.struct.Seq
import mindustry.Vars
import mindustry.content.Bullets
import mindustry.content.Fx
import mindustry.content.Liquids
import mindustry.content.StatusEffects
import mindustry.entities.Effect
import mindustry.entities.Fires
import mindustry.entities.Units
import mindustry.entities.bullet.BulletType
import mindustry.entities.effect.ParticleEffect
import mindustry.gen.*
import mindustry.gen.Unit
import mindustry.graphics.Layer
import mindustry.type.Liquid
import mindustry.type.StatusEffect

abstract class StatusZoneComp(val fxColor: Color, val fxLifetime: Float = 40f) {
    open var statusZoneSize: Float = 16f
    open var statusEffect: StatusEffect = StatusEffects.none
    open var statusEffectLength: Float = 10f
    var settings: Seq<String> = Seq.with("animated-status-zone", "animatedshields")
    var particleSettings: Seq<String> = Seq.with("status-zone-particles")
    var shieldEffect: Effect = Fx.none
    var lineEffect: Effect = Fx.none
    var particleEffect: Effect = Fx.none
    var particleChance: Double = 0.015
    var triggerFire: Boolean = false
    var triggerShock: Boolean = false
    var damageBuildings: Boolean = false
    var damageUnits: Boolean = false
    val triggerRange: Float = 12f
    val triggerChance = 0.015

    abstract fun search(): Any?

    init {
        shieldEffect = Effect(fxLifetime) { e ->
            Draw.color(fxColor)
            Draw.z(Layer.shields)
            Fill.poly(e.x, e.y, 4, statusZoneSize, 45f)
        }

        lineEffect = Effect(fxLifetime) { e ->
            Draw.color(fxColor)
            Draw.z(Layer.shields + 1f)
            Lines.poly(e.x, e.y, 4, statusZoneSize, 45f)
        }

        val particle = ParticleEffect()
        particle.colorFrom = fxColor
        particle.colorTo = fxColor

        particleEffect = particle
    }

    open fun update() {
        search()
    }

    protected fun damage(units: Seq<Unit>, buildings: Seq<Building>) {
        if (damageUnits) {
            units.each { u -> u.apply(statusEffect, statusEffectLength) }
        }

        if (damageBuildings) {
            buildings.each { b -> b.damage(statusEffect.damage) }
        }
    }
}

open class PuddleStatusZoneComp(color: Color) : StatusZoneComp(color) {
    var liquid: Liquid = Liquids.water
    private var particlesValid: Boolean = true
    private var settingsValid: Boolean = true

    override fun update() {
        settingsValid = true
        settings.each { s ->
            settingsValid = settingsValid && Core.settings.getBool(s)
        }

        particlesValid = true
        particleSettings.each { s ->
            particlesValid = particlesValid && Core.settings.getBool(s)
        }

        val puddles = search()
        val triggers = getTriggers(puddles)
        val units = searchUnits(puddles, triggers)
        val buildings = searchBuildings(puddles)

        activateTriggers(puddles)
        draw(puddles, triggers)
        damage(units, buildings)
    }

    override fun search(): Seq<Puddle> {
        val output = Seq<Puddle>()

        Groups.puddle.each { p ->
            if (p.liquid == liquid) {
                output.add(p)
            }
        }

        return output
    }

    private fun searchUnits(puddles: Seq<Puddle>, triggers: Seq<Seq<Float>>): Seq<Unit> {
        val output = Seq<Unit>()

        puddles.each { p ->
            Units.nearby(p.x - (statusZoneSize / 2), p.y - (statusZoneSize / 2), statusZoneSize, statusZoneSize) { u ->
                output.add(u)
            }
        }

        triggers.each { t ->
            if (triggerShock) Units.nearby(t.get(0) - (triggerRange / 2), t.get(1) - (triggerRange / 2), triggerRange, triggerRange) { u ->
                output.add(u)
            }
        }

        return output
    }

    private fun searchBuildings(puddles: Seq<Puddle>): Seq<Building> {
        val output = Seq<Building>()

        puddles.each { p ->
            Vars.indexer.eachBlock(null, p.x, p.y, statusZoneSize, {true}) { b ->
                output.add(b)
            }
        }

        return output
    }

    private fun getTriggers(puddles: Seq<Puddle>): Seq<Seq<Float>> {
        val output = Seq<Seq<Float>>()

        puddles.each { p ->
            val range = statusZoneSize - 5
            val randomX = Mathf.random(-range, range)
            val randomY = Mathf.random(-range, range)

            if (Mathf.chance(triggerChance)) output.add(Seq.with((p.x + randomX) / Vars.tilesize, (p.y + randomY) / Vars.tilesize))
        }

        return output
    }

    private fun activateTriggers(puddles: Seq<Puddle>) {
        getTriggers(puddles).each { t ->
            if (triggerFire) Fires.create(Vars.world.tile(t.get(0).toInt(), t.get(1).toInt()))
        }
    }

    private fun draw(puddles: Seq<Puddle>, triggers: Seq<Seq<Float>>) {
        puddles.each { p ->
            if (p is Puddle) {
                if (settingsValid) {
                    shieldEffect.at(p.x, p.y)
                } else {
                    lineEffect.at(p.x, p.y)
                }

                if (particlesValid && Mathf.chance(particleChance)) {
                    val range = statusZoneSize - 5
                    val randomX = Mathf.random(-range, range)
                    val randomY = Mathf.random(-range, range)

                    particleEffect.at(p.x + randomX, p.y + randomY)
                }

                if (triggerShock || triggerFire) {
                    triggers.each { t ->
                        if (triggerShock) Fx.hitFuse.at(t.get(0) * Vars.tilesize, t.get(1) * Vars.tilesize)
                    }
                }
            }
        }
    }
}

open class BulletStatusZoneComp(color: Color) : StatusZoneComp(color, 15f) {
    var bullet: BulletType = Bullets.standardCopper
    private var particlesValid: Boolean = true
    private var settingsValid: Boolean = true

    init {
        shieldEffect = Effect(fxLifetime) { e ->
            Draw.color(fxColor)
            Draw.z(Layer.shields)
            Fill.poly(e.x, e.y, 4, statusZoneSize, 45f)
        }

        lineEffect = Effect(fxLifetime) { e ->
            Draw.color(fxColor)
            Draw.z(Layer.shields + 1f)
            Lines.poly(e.x, e.y, 4, statusZoneSize, 45f)
        }

        particleEffect = object : ParticleEffect() {
            init {
                colorFrom = fxColor
                colorTo = fxColor
            }
        }
    }

    override fun update() {
        settingsValid = true
        settings.each { s ->
            settingsValid = settingsValid && Core.settings.getBool(s)
        }

        particlesValid = true
        particleSettings.each { s ->
            particlesValid = particlesValid && Core.settings.getBool(s)
        }

        val bullets = search()
        val triggers = getTriggers(bullets)
        val units = searchUnits(bullets, triggers)
        val buildings = searchBuildings(bullets)

        activateTriggers(bullets)
        draw(bullets, triggers)
        damage(units, buildings)
    }

    override fun search(): Seq<Bullet> {
        val output = Seq<Bullet>()

        Groups.bullet.each { b ->
            if (b.type == bullet) {
                output.add(b)
            }
        }

        return output
    }

    private fun searchUnits(bullets: Seq<Bullet>, triggers: Seq<Seq<Float>>): Seq<Unit> {
        val output = Seq<Unit>()

        bullets.each { b ->
            Units.nearby(b.x - (statusZoneSize / 2), b.y - (statusZoneSize / 2), statusZoneSize, statusZoneSize) { u ->
                output.add(u)
            }
        }

        triggers.each { t ->
            if (triggerShock) Units.nearby(t.get(0) - (triggerRange / 2), t.get(1) - (triggerRange / 2), triggerRange, triggerRange) { u ->
                output.add(u)
            }
        }

        return output
    }

    private fun searchBuildings(bullets: Seq<Bullet>): Seq<Building> {
        val output = Seq<Building>()

        bullets.each { b ->
            Vars.indexer.eachBlock(null, b.x, b.y, statusZoneSize, {true}) { build ->
                output.add(build)
            }
        }

        return output
    }

    private fun getTriggers(bullets: Seq<Bullet>): Seq<Seq<Float>> {
        val output = Seq<Seq<Float>>()

        bullets.each { b ->
            val range = statusZoneSize - 5
            val randomX = Mathf.random(-range, range)
            val randomY = Mathf.random(-range, range)

            if (Mathf.chance(triggerChance)) output.add(Seq.with((b.x + randomX) / Vars.tilesize, (b.y + randomY) / Vars.tilesize))
        }

        return output
    }

    private fun activateTriggers(bullets: Seq<Bullet>) {
        getTriggers(bullets).each { t ->
            if (triggerFire) Fires.create(Vars.world.tile(t.get(0).toInt(), t.get(1).toInt()))
        }
    }

    private fun draw(bullets: Seq<Bullet>, triggers: Seq<Seq<Float>>) {
        bullets.each { b ->
            if (b.type == bullet) {
                if (settingsValid) {
                    shieldEffect.at(b.x, b.y)
                } else {
                    lineEffect.at(b.x, b.y)
                }

                if (particlesValid && Mathf.chance(particleChance)) {
                    val range = statusZoneSize - 5
                    val randomX = Mathf.random(-range, range)
                    val randomY = Mathf.random(-range, range)

                    particleEffect.at(b.x + randomX, b.y + randomY)
                }

                if (triggerShock || triggerFire) {
                    triggers.each { t ->
                        if (triggerShock) Fx.hitFuse.at(t.get(0) * Vars.tilesize, t.get(1) * Vars.tilesize)
                    }
                }
            }
        }
    }
}