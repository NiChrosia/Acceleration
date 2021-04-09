package acceleration

import arc.util.Log
import mindustry.mod.Mod

import acceleration.content.*
import acceleration.graphics.Drawm
import arc.Events
import mindustry.game.EventType

class Acceleration : Mod() {
    init {
        Log.info("Mod [accent]Acceleration[] constructor loaded successfully.")
    }
    
    override fun loadContent() {
        AccelerationColors().load()
        AccelerationFx().load()
        AccelerationTeams().load()
        AccelerationStatusEffects().load()
        AccelerationLiquids().load()
        AccelerationBullets().load()
        AccelerationUnits().load()
        AccelerationItems().load()
        AccelerationBlocks().load()
        AccelerationStatusZones().load()
        AccelerationPlanets().load()
        AccelerationSectors().load()
        AccelerationTechTree().load()

        Events.on(EventType.ClientLoadEvent().javaClass) {
            AccelerationSettings().load()
        }

        Log.info("Mod [accent]Acceleration[] loaded content successfully.")
    }
}