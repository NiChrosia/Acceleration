package acceleration.content

import acceleration.entities.OrbBulletType
import arc.graphics.Color
import mindustry.Vars
import mindustry.content.Bullets
import mindustry.content.Fx
import mindustry.content.StatusEffects
import mindustry.ctype.ContentList
import mindustry.graphics.Pal
import mindustry.io.JsonIO
import mindustry.entities.bullet.*

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

        quarkLaserBolt = object : LaserBoltBulletType() {
            init {
                damage = 13f
                frontColor = Color.white
                backColor = Pal.heal
                lifetime = 70f
                speed = 5.4f
                buildingDamageMultiplier = 0.01f
                collidesTeam = true
                healPercent = 1f
            }
        }

        lightEnergyLaser = object : LaserBoltBulletType() {
            init {
                damage = 12f
                frontColor = Color.white
                backColor = Pal.lancerLaser
                lifetime = 20f
                speed = 5.6f
            }
        }

        // Shock
        shockPierceBullet = object : BasicBulletType() {
            init {
                width = 11f
                height = 13f

                damage = 12f
                speed = 3.7f
                pierceCap = 2
                pierce = true
                shootEffect = Fx.shootBig
            }
        }

        shockPierceLargeBullet = object : FlakBulletType() {
            init {
                lifetime = 85f

                damage = 22f
                speed = 3.2f
                pierceCap = 3
                pierce = true
                shootEffect = Fx.shootBig
                collidesGround = true
                lightning = 1
                lightningLength = 4

                splashDamage = 40f * 1.5f
                splashDamageRadius = 26f
                explodeRange = 25f

                status = StatusEffects.shocked

                trailEffect = AccelerationFx.shockTrail
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
                status = StatusEffects.sporeSlowed

                frontColor = Pal.spore
                backColor = Pal.spore
            }
        }

        pyraStatusZone = object : BasicBulletType() {
            init {
                speed = 5.5f
                damage = 33f
                width = 10f
                height = 13f
                incendChance = 1f
                incendAmount = 1
                knockback = 0.8f
                status = StatusEffects.burning

                frontColor = Pal.lighterOrange
                backColor = Pal.lightOrange
            }
        }

        thoriumStatusZone = object : BasicBulletType() {
            init {
                speed = 5f
                damage = 36f
                width = 10f
                height = 13f
                ammoMultiplier = 4f
                knockback = 1f
                status = StatusEffects.corroded
            }
        }

        surgeStatusZone = object : BasicBulletType() {
            init {
                ammoMultiplier = 5f
                speed = 5.25f
                damage = 10f
                width = 10f
                height = 13f
                ammoMultiplier = 1f
                lightning = 3
                lightningLength = 6
                knockback = 0.8f
                status = StatusEffects.shocked
            }
        }

        // Overload Bullets
        overloadBullet = object : LaserBoltBulletType() {
            init {
                width = 2.25f
                height = 6f

                damage = 34f
                speed = 3.8f

                pierce = true

                status = AccelerationStatusEffects.overloaded

                despawnEffect = AccelerationFx.overloadLaserHit
                smokeEffect = AccelerationFx.overloadLaserCharge
            }
        }

        cryoMissile = object : MissileBulletType() {
            init {
                damage = 89f
                speed = 4.2f

                lifetime = 85f

                frontColor = AccelerationPal.arctifluid
                backColor = Color.white

                lightningColor = AccelerationPal.arctifluid
                lightning = 1
                lightningLength = 6
                lightningDamage = 5f

                status = AccelerationStatusEffects.arctifreezing

                despawnEffect = AccelerationFx.cryoLaserHit

                trailColor = AccelerationPal.cryo
            }
        }

        overloadLaserLight = object : LaserBoltBulletType() {
            init {
                width = 2f
                height = 6f

                damage = 24f
                speed = 3.8f

                pierce = true

                frontColor = AccelerationPal.overdrive
                backColor = Color.white

                status = AccelerationStatusEffects.overloaded

                despawnEffect = AccelerationFx.overloadLaserHit
                smokeEffect = AccelerationFx.overloadLaserCharge
            }
        }

        cryoenergyBulletFrag = object : BombBulletType()  {
            init {
                sprite = "large-bomb"

                lifetime = 0f
                splashDamage = 225f
                splashDamageRadius = 25f

                collidesAir = true

                despawnEffect = AccelerationFx.cryoHexagonInverted
            }
        }

        // Cryo Bullets
        cryoenergyBullet = object : OrbBulletType() {
            init {
                size = 6f

                damage = 45f
                speed = 3.8f
                lifetime = 65f

                pierce = true
                recoil = 0.2f

                status = AccelerationStatusEffects.arctifreezing

                smokeEffect = AccelerationFx.cryoLaserCharge

                fragBullet = cryoenergyBulletFrag
                fragBullets = 1

                color = AccelerationPal.cryo

                lightning = 5
                lightningColor = AccelerationPal.cryo
                lightningLength = 9
                lightningDamage = 36f
            }
        }

        cryoRail = object : RailBulletType() {
            init {
                shootEffect = AccelerationFx.cryorailShoot

                length = 250f
                updateEffectSeg = 35f
                pierceEffect = AccelerationFx.cryorailHit
                updateEffect = AccelerationFx.cryorailTrail
                hitEffect = Fx.massiveExplosion

                smokeEffect = Fx.shootBig2

                recoil = 0.5f

                damage = 650f
                pierceDamageFactor = 0.5f
            }
        }

        voltaicRail = object : PointBulletType() {
            init {
                shootEffect = AccelerationFx.voltaicShoot
                hitEffect = AccelerationFx.voltaicHit
                smokeEffect = Fx.smokeCloud
                trailEffect = AccelerationFx.voltaicTrail
                despawnEffect = AccelerationFx.voltaicBomb

                trailSpacing = 25f

                damage = 1250f

                speed = range()
                hitShake = 7f
                ammoMultiplier = 1f

                buildingDamageMultiplier = 0.05f

                lightning = 4
                lightningLength = 8
                lightningDamage = 185f

                splashDamage = 120f
                splashDamageRadius = 24f
            }
        }
    }

    companion object {
        lateinit var standardLaserBolt : LaserBoltBulletType
        lateinit var quarkLaserBolt : LaserBoltBulletType
        lateinit var lightEnergyLaser : LaserBoltBulletType

        lateinit var shockPierceBullet : BasicBulletType
        lateinit var shockPierceLargeBullet : FlakBulletType

        lateinit var railArtilleryDense : ArtilleryBulletType
        lateinit var railArtilleryHoming : ArtilleryBulletType
        lateinit var railArtilleryIncendiary : ArtilleryBulletType
        lateinit var railArtilleryGlass : ArtilleryBulletType

        lateinit var sporeStatusZone : BasicBulletType
        lateinit var pyraStatusZone : BasicBulletType
        lateinit var thoriumStatusZone : BasicBulletType
        lateinit var surgeStatusZone : BasicBulletType

        lateinit var overloadBullet : LaserBoltBulletType
        lateinit var overloadLaserLight : LaserBoltBulletType

        lateinit var cryoenergyBulletFrag : BombBulletType
        lateinit var cryoenergyBullet : OrbBulletType
        lateinit var cryoMissile : MissileBulletType
        lateinit var cryoRail : RailBulletType

        lateinit var voltaicRail : PointBulletType
    }
}