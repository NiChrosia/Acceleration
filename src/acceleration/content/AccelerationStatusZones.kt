package acceleration.content

import acceleration.content.entities.comp.BulletStatusZoneComp
import acceleration.content.entities.comp.PuddleStatusZoneComp
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

        Events.run(EventType.Trigger.update) {
            if (!Vars.state.isPaused) {
                arctifluidStatusZone.update()
                quarkPlasmaStatusZone.update()
                sporeStatusZone.update()
            }
        }
    }

    companion object {
        lateinit var arctifluidStatusZone : PuddleStatusZoneComp
        lateinit var quarkPlasmaStatusZone : PuddleStatusZoneComp

        lateinit var sporeStatusZone : BulletStatusZoneComp
    }
}