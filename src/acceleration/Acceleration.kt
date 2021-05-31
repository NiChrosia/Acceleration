package acceleration

import arc.util.Log
import mindustry.mod.Mod
import acceleration.content.*
import acceleration.type.modularunit.ModularUnitModules
import acceleration.type.modularunit.ModularUnitProperties
import acceleration.ui.AccelerationUI
import arc.Events
import mindustry.Vars
import mindustry.game.EventType

open class Acceleration : Mod() {
    private val accent = if (Vars.net.client()) "[accent]" else ""
    private val end = if (Vars.net.client()) "[]" else ""

    companion object {
        lateinit var ui: AccelerationUI
        lateinit var modularUnitProperties: ModularUnitProperties
        lateinit var modularUnitModules: ModularUnitModules
        const val modName = "acceleration"
    }

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

        // load on ContentInitEvent so sprites are loaded
        Events.on(EventType.ContentInitEvent::class.java) {
            modularUnitProperties = ModularUnitProperties()
            modularUnitModules = ModularUnitModules()
        }

        Events.on(EventType.ClientLoadEvent()::class.java) {
            AccelerationSettings().load()

            ui = AccelerationUI()
        }

        Events.run(EventType.Trigger.update) {
            modularUnitProperties.update()
        }

        Log.info("Mod ${accent}Acceleration${end} loaded content successfully.")
    }
}