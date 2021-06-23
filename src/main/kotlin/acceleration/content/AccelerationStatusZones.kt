package acceleration.content

import acceleration.entities.statuszone.BulletStatusZone
import acceleration.entities.statuszone.PuddleStatusZone
import arc.Events
import mindustry.Vars
import mindustry.content.StatusEffects
import mindustry.ctype.ContentList
import mindustry.game.EventType
import mindustry.graphics.Pal

class AccelerationStatusZones : ContentList {
    override fun load() {
        arctifluidStatusZone = PuddleStatusZone(
            liquid = AccelerationLiquids.arctifluid,
            damageBuildings = false,
            damageUnits = true,
            statusEffect = AccelerationStatusEffects.arctifreezing,
            size = 32f
        )

        quarkPlasmaStatusZone = PuddleStatusZone(
            color = AccelerationPal.quarkPlasma,
            liquid = AccelerationLiquids.quarkPlasma,
            damageBuildings = true,
            damageUnits = true,
            statusEffect = AccelerationStatusEffects.liquefying,
            size = 32f
        )

        sporeStatusZone = BulletStatusZone(
            color = Pal.spore,
            bullet = AccelerationBullets.sporeStatusZone,
            damageBuildings = false,
            damageUnits = true,
            statusEffect = StatusEffects.sporeSlowed,
            size = 24f
        )

        pyraStatusZone = BulletStatusZone(
            color = Pal.lightOrange,
            bullet = AccelerationBullets.pyraStatusZone,
            damageBuildings = false,
            damageUnits = true,
            statusEffect = StatusEffects.burning,
            size = 24f
        )

        thoriumStatusZone = BulletStatusZone(
            color = Pal.thoriumPink,
            bullet = AccelerationBullets.thoriumStatusZone,
            damageBuildings = false,
            damageUnits = true,
            statusEffect = StatusEffects.corroded,
            size = 24f
        )

        surgeStatusZone = BulletStatusZone(
            color = Pal.surge,
            bullet = AccelerationBullets.surgeStatusZone,
            damageBuildings = false,
            damageUnits = true,
            statusEffect = StatusEffects.shocked,
            size = 24f
        )

        shockPierceLargeStatusZone = BulletStatusZone(
            color = Pal.surge,
            bullet = AccelerationBullets.shockPierceLargeBullet,
            damageBuildings = false,
            damageUnits = true,
            statusEffect = StatusEffects.shocked,
            size = 24f
        )

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
        lateinit var arctifluidStatusZone : PuddleStatusZone
        lateinit var quarkPlasmaStatusZone : PuddleStatusZone

        lateinit var sporeStatusZone : BulletStatusZone
        lateinit var pyraStatusZone : BulletStatusZone
        lateinit var thoriumStatusZone : BulletStatusZone
        lateinit var surgeStatusZone : BulletStatusZone

        lateinit var shockPierceLargeStatusZone : BulletStatusZone
    }
}