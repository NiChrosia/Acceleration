package acceleration.content

import arc.func.Prov
import arc.struct.ObjectSet
import arc.struct.Seq
import mindustry.Vars
import mindustry.ai.types.BuilderAI
import mindustry.content.Items
import mindustry.content.StatusEffects
import mindustry.ctype.ContentList
import mindustry.entities.abilities.ForceFieldAbility
import mindustry.entities.abilities.StatusFieldAbility
import mindustry.gen.Sounds
import mindustry.gen.UnitEntity
import mindustry.type.AmmoTypes
import mindustry.type.UnitType
import mindustry.type.Weapon
import mindustry.gen.EntityMapping

class AccelerationUnits : ContentList {
    private fun setEntity(name: String, c: Prov<*>) {
        EntityMapping.nameMap.put(name, c)
    }

    override fun load() {
        setEntity("quark") { UnitEntity.create() }
        quark = object : UnitType("quark") {
            init {
                defaultController = Prov { BuilderAI() }
                flying = true
                speed = 3.75f
                rotateSpeed = 21f
                drag = 0.05f
                accel = 0.12f
                health = 250f
                range = 330f
                aimDst = 25f
                commandLimit = 6
                ammoType = AmmoTypes.power
                itemCapacity = 90
                mineTier = 3
                mineSpeed = 10f
                buildSpeed = 1.25f
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

                range = 380f
                ammoType = AmmoTypes.ItemAmmoType(Items.surgeAlloy)
                weapons = Seq.with(
                    object : Weapon() {
                        init {
                            inaccuracy = 2f
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
                            inaccuracy = 16f
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
    }

    companion object {
        lateinit var quark : UnitType

        // Controller Tree
        lateinit var ion : UnitType
        lateinit var spark : UnitType
    }
}