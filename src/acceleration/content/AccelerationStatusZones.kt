package acceleration.content

import acceleration.entities.comp.BulletStatusZoneComp
import acceleration.entities.comp.PuddleStatusZoneComp
import arc.Events
import arc.util.Log
import mindustry.Vars
import mindustry.content.StatusEffects
import mindustry.ctype.ContentList
import mindustry.game.EventType
import mindustry.graphics.Pal

class AccelerationStatusZones : ContentList {
    override fun load() {
        arctifluidStatusZone = object : PuddleStatusZoneComp(AccelerationColors.arctifluidColor) {
            init {
                liquid = AccelerationLiquids.arctifluid
                damageBuildings = false
                damageUnits = true
                statusEffect = AccelerationStatusEffects.arctifreezing
                statusZoneSize = 32f
            }
        }

        quarkPlasmaStatusZone = object : PuddleStatusZoneComp(AccelerationColors.quarkPlasmaColor) {
            init {
                liquid = AccelerationLiquids.quarkPlasma
                damageBuildings = true
                damageUnits = true
                statusEffect = AccelerationStatusEffects.liquefying
                statusZoneSize = 32f
                triggerShock = true
            }
        }

        sporeStatusZone = object : BulletStatusZoneComp(Pal.spore) {
            init {
                bullet = AccelerationBullets.sporeStatusZone
                damageBuildings = false
                damageUnits = true
                statusEffect = StatusEffects.sporeSlowed
                statusZoneSize = 24f
            }
        }

        pyraStatusZone = object : BulletStatusZoneComp(Pal.lightOrange) {
            init {
                bullet = AccelerationBullets.pyraStatusZone
                damageBuildings = false
                damageUnits = true
                statusEffect = StatusEffects.burning
                statusZoneSize = 24f
                triggerFire = true
            }
        }

        thoriumStatusZone = object : BulletStatusZoneComp(Pal.thoriumPink) {
            init {
                bullet = AccelerationBullets.thoriumStatusZone
                damageBuildings = false
                damageUnits = true
                statusEffect = StatusEffects.corroded
                statusZoneSize = 24f
            }
        }

        surgeStatusZone = object : BulletStatusZoneComp(Pal.surge) {
            init {
                bullet = AccelerationBullets.surgeStatusZone
                damageBuildings = false
                damageUnits = true
                statusEffect = StatusEffects.shocked
                statusZoneSize = 24f
                triggerShock = true
            }
        }

        Events.run(EventType.Trigger.update) {
            if (!Vars.state.isPaused) {
                arctifluidStatusZone.update()
                quarkPlasmaStatusZone.update()
                sporeStatusZone.update()
                pyraStatusZone.update()
                thoriumStatusZone.update()
                surgeStatusZone.update()
            }
        }
    }

    companion object {
        lateinit var arctifluidStatusZone : PuddleStatusZoneComp
        lateinit var quarkPlasmaStatusZone : PuddleStatusZoneComp

        lateinit var sporeStatusZone : BulletStatusZoneComp
        lateinit var pyraStatusZone : BulletStatusZoneComp
        lateinit var thoriumStatusZone : BulletStatusZoneComp
        lateinit var surgeStatusZone : BulletStatusZoneComp
    }
}