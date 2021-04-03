package acceleration.content

import arc.graphics.Color
import arc.util.Log
import mindustry.Vars
import mindustry.content.Bullets
import mindustry.content.Fx
import mindustry.content.StatusEffects
import mindustry.ctype.ContentList
import mindustry.entities.bullet.ArtilleryBulletType
import mindustry.entities.bullet.BasicBulletType
import mindustry.entities.bullet.LaserBoltBulletType
import mindustry.graphics.Pal
import mindustry.io.JsonIO

class AccelerationBullets : ContentList {
    override fun load() {
        // Laser Bolt
        standardLaserBolt = object : LaserBoltBulletType() {
            init {
                damage = 6f
                frontColor = Color.white
                backColor = Pal.lancerLaser
                lifetime = 30f
                speed = 5.4f
            }
        }

        // Railgun Artillery
        railArtilleryDense = object : ArtilleryBulletType() {
            init {
                lifetime = 80f
                speed = 8f
                damage = 45f
                hitEffect = Fx.flakExplosion
                splashDamageRadius = 1.9f * Vars.tilesize
                splashDamage = 22f
                ammoMultiplier = 2f
                knockback = 0.8f
                trailMult = 0.5f
            }
        }

        railArtilleryHoming = object : ArtilleryBulletType() {
            init {
                lifetime = 80f
                speed = 6.5f
                damage = 40f
                hitEffect = Fx.flakExplosion
                homingPower = 0.24f
                homingRange = 40f
                splashDamageRadius = 1.9f * Vars.tilesize
                splashDamage = 22f
                ammoMultiplier = 3f
                knockback = 0.8f
                trailMult = 0.5f
            }
        }

        railArtilleryIncendiary = object : ArtilleryBulletType() {
            init {
                lifetime = 80f
                speed = 7.5f
                damage = 50f
                hitEffect = Fx.flakExplosion
                status = StatusEffects.burning
                frontColor = Pal.lightishOrange
                backColor = Pal.lightOrange
                trailEffect = Fx.incendTrail
                splashDamageRadius = 1.9f * Vars.tilesize
                splashDamage = 25f
                ammoMultiplier = 2f
                knockback = 0.8f
                trailMult = 0.5f
            }
        }

        val railArtilleryGlassFrag = BasicBulletType(3f, 5f)
        JsonIO.copy(Bullets.flakGlassFrag, railArtilleryGlassFrag)
        railArtilleryGlassFrag.collidesGround = true
        railArtilleryGlassFrag.collidesAir = false

        railArtilleryGlass = object : ArtilleryBulletType() {
            init {
                lifetime = 80f
                speed = 9f
                damage = 45f
                hitEffect = Fx.flakExplosion
                fragBullet = railArtilleryGlassFrag
                frontColor = Color.white
                backColor = Color.white
                splashDamageRadius = 2.7f * Vars.tilesize
                splashDamage = 33f
                ammoMultiplier = 2f
                knockback = 0.9f
                trailMult = 0.5f
            }
        }

        // Status Zone Bullets
        sporeStatusZone = object : BasicBulletType() {
            init {
                speed = 3.75f
                damage = 2f
                width = 10f
                height = 13f
                ammoMultiplier = 4f
                knockback = 1f
            }
        }
    }

    companion object {
        lateinit var standardLaserBolt : LaserBoltBulletType
        lateinit var railArtilleryDense : ArtilleryBulletType
        lateinit var railArtilleryHoming : ArtilleryBulletType
        lateinit var railArtilleryIncendiary : ArtilleryBulletType
        lateinit var railArtilleryGlass : ArtilleryBulletType
        lateinit var sporeStatusZone : BasicBulletType
    }
}