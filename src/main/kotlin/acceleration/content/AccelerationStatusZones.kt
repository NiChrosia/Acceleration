package acceleration.content

import acceleration.entities.comp.BulletStatusZoneComp
import acceleration.entities.comp.PuddleStatusZoneComp
import arc.Events
import mindustry.Vars
import mindustry.content.StatusEffects
import mindustry.ctype.ContentList
import mindustry.game.EventType
import mindustry.graphics.Pal

class AccelerationStatusZones : ContentList {
    override fun load() {
        arctifluidStatusZone = PuddleStatusZoneComp(AccelerationPal.arctifluid).apply {
            liquid = AccelerationLiquids.arctifluid
            damageBuildings = false
            damageUnits = true
            statusEffect = AccelerationStatusEffects.arctifreezing
            statusZoneSize = 32f
        }

        quarkPlasmaStatusZone = PuddleStatusZoneComp(AccelerationPal.quarkPlasma).apply {
            liquid = AccelerationLiquids.quarkPlasma
            damageBuildings = true
            damageUnits = true
            statusEffect = AccelerationStatusEffects.liquefying
            statusZoneSize = 32f
            triggerShock = true
        }

        sporeStatusZone = BulletStatusZoneComp(Pal.spore).apply {
            bullet = AccelerationBullets.sporeStatusZone
            damageBuildings = false
            damageUnits = true
            statusEffect = StatusEffects.sporeSlowed
            statusZoneSize = 24f
        }

        pyraStatusZone = BulletStatusZoneComp(Pal.lightOrange).apply {
            bullet = AccelerationBullets.pyraStatusZone
            damageBuildings = false
            damageUnits = true
            statusEffect = StatusEffects.burning
            statusZoneSize = 24f
            triggerFire = true
        }

        thoriumStatusZone = BulletStatusZoneComp(Pal.thoriumPink).apply {
            bullet = AccelerationBullets.thoriumStatusZone
            damageBuildings = false
            damageUnits = true
            statusEffect = StatusEffects.corroded
            statusZoneSize = 24f
        }

        surgeStatusZone = BulletStatusZoneComp(Pal.surge).apply {
            bullet = AccelerationBullets.surgeStatusZone
            damageBuildings = false
            damageUnits = true
            statusEffect = StatusEffects.shocked
            statusZoneSize = 24f
            triggerShock = true
        }

        shockPierceLargeStatusZone = BulletStatusZoneComp(Pal.surge).apply {
            bullet = AccelerationBullets.shockPierceLargeBullet
            damageBuildings = false
            damageUnits = true
            statusEffect = StatusEffects.shocked
            statusZoneSize = 24f
            triggerShock = true
        }

        Events.run(EventType.Trigger.update) {
            if (!Vars.state.isPaused) {
                arctifluidStatusZone.update()
                quarkPlasmaStatusZone.update()

                sporeStatusZone.update()
                pyraStatusZone.update()
                thoriumStatusZone.update()
                surgeStatusZone.update()

                shockPierceLargeStatusZone.update()
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

        lateinit var shockPierceLargeStatusZone : BulletStatusZoneComp
    }
}