package acceleration

import arc.util.Log
import mindustry.mod.Mod
import acceleration.content.*
import arc.Events
import mindustry.Vars
import mindustry.game.EventType

class Acceleration : Mod() {
    private val accent = if (Vars.net.client()) "[accent]" else ""
    private val end = if (Vars.net.client()) "[]" else ""

    init {
        Log.info("Mod ${accent}Acceleration${end} constructor loaded successfully.")
    }
    
    override fun loadContent() {
        AccelerationPal().load()
        AccelerationFx().load()
        AccelerationTeams().load()
        AccelerationStatusEffects().load()
        AccelerationLiquids().load()
        AccelerationBullets().load()
        AccelerationUnitTypes().load()
        AccelerationItems().load()
        AccelerationBlocks().load()
        AccelerationStatusZones().load()
        AccelerationPlanets().load()
        AccelerationSectors().load()
        AccelerationTechTree().load()

        Events.on(EventType.ClientLoadEvent().javaClass) {
            AccelerationSettings().load()
        }

        Log.info("Mod ${accent}Acceleration${end} loaded content successfully.")

        Any().takeUnless { true }
    }
}