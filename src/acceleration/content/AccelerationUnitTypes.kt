package acceleration.content

import acceleration.entities.abilities.LightningFieldAbility
import arc.func.Prov
import arc.struct.ObjectSet
import arc.struct.Seq
import mindustry.ai.types.BuilderAI
import mindustry.content.Items
import mindustry.content.StatusEffects
import mindustry.ctype.ContentList
import mindustry.entities.Units
import mindustry.entities.abilities.ForceFieldAbility
import mindustry.entities.abilities.RepairFieldAbility
import mindustry.entities.abilities.ShieldRegenFieldAbility
import mindustry.entities.abilities.StatusFieldAbility
import mindustry.gen.Sounds
import mindustry.gen.UnitEntity
import mindustry.type.AmmoTypes
import mindustry.type.UnitType
import mindustry.type.Weapon
import mindustry.gen.EntityMapping
import mindustry.gen.Unit

class AccelerationUnitTypes : ContentList {
    private fun setEntity(name: String, c: Prov<*>) {
        EntityMapping.nameMap.put(name, c)
    }

    override fun load() {
        setEntity("quark") { UnitEntity.create() }
        quark = object : UnitType("quark") {
            init {
                speed = 3.75f
                rotateSpeed = 21f
                drag = 0.05f
                accel = 0.12f

                defaultController = Prov { BuilderAI() }
                flying = true
                health = 250f
                range = 330f

                aimDst = 25f
                commandLimit = 6
                ammoType = AmmoTypes.power
                itemCapacity = 90

                mineTier = 3
                mineSpeed = 10f
                buildSpeed = 1.25f
                mineSpeed = 4f

                hitSize = 11f
                canHeal = true
                weapons = Seq.with(
                    object : Weapon() {
                        init {
                            name = "acceleration-heal-repeater"
                            bullet = AccelerationBullets.quarkLaserBolt
                            reload = 16f
                            x = 1f
                            y = 2f
                            shootSound = Sounds.lasershoot
                        }
                    }
                )
            }
        }

        // Controller Tree

        setEntity("ion") { UnitEntity.create() }
        ion = object : UnitType("ion") {
            init {
                defaultController = Prov { BuilderAI() }
                flying = true
                speed = 3.4f
                accel = 0.05f
                drag = 0.03f
                rotateSpeed = 14f

                health = 240f
                armor = 2f
                hitSize = 9f
                canHeal = true

                itemCapacity = 40
                mineTier = 2
                buildSpeed = 0.85f
                mineSpeed = 5f
                commandLimit = 4

                range = 380f
                ammoType = AmmoTypes.power
                rotateShooting = false
                weapons = Seq.with(
                    object : Weapon() {
                        init {
                            name = "acceleration-light-energy-laser-weapon"
                            bullet = AccelerationBullets.lightEnergyLaser
                            reload = 18f
                            x = 1f
                            y = 2f
                            rotate = true
                            shootSound = Sounds.lasershoot
                        }
                    }
                )
            }
        }

        setEntity("spark") { UnitEntity.create() }
        spark = object : UnitType("spark") {
            init {
                defaultController = Prov { BuilderAI() }
                flying = true
                speed = 3.75f
                accel = 0.055f
                drag = 0.032f
                rotateSpeed = 18f

                health = 780f
                armor = 5f
                hitSize = 13f
                canHeal = true

                itemCapacity = 70
                mineTier = 3
                buildSpeed = 1.5f
                mineSpeed = 6f
                commandLimit = 5

                range = 380f
                ammoType = AmmoTypes.ItemAmmoType(Items.silicon)
                weapons = Seq.with(
                    object : Weapon() {
                        init {
                            name = "acceleration-shock-pierce-large"
                            bullet = AccelerationBullets.shockPierceLargeBullet
                            reload = 288f
                            x = 2f
                            y = -3f
                            shootSound = Sounds.shootSnap
                        }
                    },
                    object : Weapon() {
                        init {
                            inaccuracy = 2f
                            name = "acceleration-shock-pierce-small"
                            bullet = AccelerationBullets.shockPierceBullet
                            reload = 18f
                            x = 3f
                            y = 2f
                            shootSound = Sounds.shootSnap
                        }
                    }
                )

                abilities = Seq.with(
                    ForceFieldAbility(24f, 0.2f, 450f, 20f * 60f),
                    StatusFieldAbility(StatusEffects.overclock, 60f * 2, 60f * 3, 36f)
                )

                immunities = ObjectSet.with(StatusEffects.shocked)
            }
        }

        setEntity("plasma") { UnitEntity.create() }
        plasma = object : UnitType("plasma") {
            private val zoneSize = 36f
            private val zoneDamage = 1f

            init {
                flying = true

                speed = 4f
                accel = 0.065f
                drag = 0.035f

                rotateSpeed = 24f
                health = 2400f
                armor = 9f
                hitSize = 17f

                itemCapacity = 100
                mineTier = 3
                buildSpeed = 2.25f
                mineSpeed = 9f
                commandLimit = 6

                range = 150f
                ammoType = AmmoTypes.power
                weapons = Seq.with(
                    object : Weapon() {
                        init {
                            name = "overloading-weapon"
                            bullet = AccelerationBullets.overloadBullet
                            reload = 36f
                            x = 3f
                            alternate = true
                            shootSound = Sounds.lasershoot
                            firstShotDelay = 10f
                        }
                    }
                )
            }

            override fun update(unit: Unit) {
                super.update(unit)

                Units.nearby(unit.x - zoneSize / 2, unit.y - zoneSize / 2, zoneSize, zoneSize) { u ->
                    if (u.team == unit.team) return@nearby

                    u.damagePierce(zoneDamage)
                }
            }
        }

        setEntity("discharge") { UnitEntity.create() }
        discharge = object : UnitType("discharge") {
            init {
                flying = true

                speed = 4.15f
                accel = 0.08f
                drag = 0.037f

                rotateSpeed = 36f
                health = 13500f
                armor = 18f
                hitSize = 24f

                itemCapacity = 250
                mineTier = 4
                buildSpeed = 4.75f
                mineSpeed = 12f
                commandLimit = 12

                range = 150f
                ammoType = AmmoTypes.powerHigh
                weapons = Seq.with(
                    object : Weapon() {
                        init {
                            name = "cryo-missile-swarmer"
                            bullet = AccelerationBullets.cryoMissile
                            reload = 18f
                            alternate = true
                            inaccuracy = 6f

                            x = 6f
                            y = 4f

                            shootSound = Sounds.lasershoot
                        }
                    },

                    object : Weapon() {
                        init {
                            name = "overload-laser-machine-gun"
                            bullet = AccelerationBullets.overloadLaserLight
                            reload = 16f
                            alternate = true

                            x = 10f
                            y = -12f

                            shootSound = Sounds.lasershoot
                        }
                    }
                )

                abilities = Seq.with(
                    ForceFieldAbility(64f, 0.6f, 1600f, 60f * 30f),
                    ShieldRegenFieldAbility(32f, 360f, 60f * 12, 90f)
                )
            }
        }

        setEntity("aurora") { UnitEntity.create() }
        aurora = object : UnitType("aurora") {
            init {
                flying = true

                speed = 4.45f
                accel = 0.1f
                drag = 0.04f

                rotateSpeed = 54f
                health = 32600f
                armor = 32f
                hitSize = 45f

                itemCapacity = 750
                mineTier = 5 // For mining modded ores.
                buildSpeed = 6.5f
                mineSpeed = 15f
                commandLimit = 8

                range = 100f
                ammoType = AmmoTypes.powerHigh
                weapons = Seq.with(
                    object : Weapon() {
                        init {
                            name = "cryo-energy-machine-gun"
                            bullet = AccelerationBullets.cryoenergyBullet
                            reload = 35f
                            alternate = true
                            inaccuracy = 5f

                            x = 10f
                            y = 2f

                            shootSound = Sounds.lasershoot
                        }
                    },

                    object : Weapon() {
                        init {
                            name = "cryo-swarmer-enhanced"
                            bullet = AccelerationBullets.cryoMissile

                            reload = 14f
                            rotateSpeed = 2.5f
                            shotDelay = 3f
                            shots = 4
                            xRand = 1f
                            inaccuracy = 8f

                            alternate = true

                            x = 16f
                            y = -12f
                            rotate = true

                            shootSound = Sounds.railgun
                        }
                    }
                )

                abilities = Seq.with(
                    ShieldRegenFieldAbility(64f, 1440f, 60f * 8, 120f),
                    StatusFieldAbility(StatusEffects.overdrive, 60f, 60f, 80f),
                    RepairFieldAbility(120f, 60f * 0.75f, 320f),
                    LightningFieldAbility(12,
                        15f,
                        20f,
                        1.5f,
                        AccelerationPal.cryo
                    )
                )
            }
        }
    }

    companion object {
        lateinit var quark : UnitType

        // Controller Tree
        lateinit var ion : UnitType
        lateinit var spark : UnitType
        lateinit var plasma : UnitType
        lateinit var discharge : UnitType
        lateinit var aurora : UnitType
    }
}