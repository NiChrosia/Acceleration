package acceleration

import arc.util.Log
import mindustry.mod.Mod
import acceleration.content.*
import acceleration.game.Blueprints
import acceleration.ui.AccelerationUI
import acceleration.func.stripIf
import arc.Events
import mindustry.Vars
import mindustry.game.EventType

open class Acceleration : Mod() {
    companion object {
        lateinit var ui: AccelerationUI
        lateinit var blueprints: Blueprints

        const val modName = "acceleration"
    }

    init {
        Log.info("Mod [accent]Acceleration[] constructor loaded successfully.".stripIf(Vars.headless))
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
        loadBlocks()
        AccelerationStatusZones().load()
        AccelerationTechTree().load()

        // load on ContentInitEvent so sprites are loaded
        Events.on(EventType.ContentInitEvent::class.java) {
            blueprints = Blueprints()
        }

        Events.on(EventType.ClientLoadEvent::class.java) {
            AccelerationSettings().load()

            ui = AccelerationUI()
        }

        Events.run(EventType.Trigger.update) {
            ui.modularUnitFactory.blueprint.update()
        }

        Log.info("Mod [accent]Acceleration[] loaded content successfully.".stripIf(Vars.headless))
    }
}