package acceleration

import arc.util.Log
import mindustry.mod.Mod
import acceleration.content.*
import arc.Events
import mindustry.Vars
import mindustry.game.EventType

class Acceleration : Mod() {
    init {
        if (Vars.net.client()) Log.info("Mod [accent]Acceleration[] constructor loaded successfully.") else Log.info("Mod Acceleration constructor loaded successfully.")
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

        if (Vars.net.client()) Log.info("Mod [accent]Acceleration[] loaded content successfully.") else Log.info("Mod Acceleration loaded content successfully.")
    }
}