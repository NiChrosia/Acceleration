package acceleration.content

import arc.func.Prov
import arc.struct.Seq
import mindustry.ai.types.BuilderAI
import mindustry.content.UnitTypes
import mindustry.ctype.ContentList
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
                hitSize = 13f
                canHeal = true
                weapons = Seq.with(
                    object : Weapon() {
                        init {
                            name = "acceleration-heal-repeater"
                            bullet = AccelerationBullets.standardLaserBolt
                            reload = 16f
                            x = 1f
                            y = 2f
                            shootSound = Sounds.lasershoot
                        }
                    }
                )

                constructor = UnitTypes.gamma.constructor // No idea how to implement this in a kotlin mod normally
            }
        }
    }

    companion object {
        lateinit var quark : UnitType
    }
}