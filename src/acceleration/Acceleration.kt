package acceleration

import arc.util.Log
import mindustry.mod.Mod

import acceleration.content.*

class Acceleration : Mod() {
    init {
        Log.info("Mod [accent]Acceleration[] [sky]Kotlin[] constructor loaded successfully.")
    }
    
    override fun loadContent() {
        AccelerationColors().load()
        AccelerationFx().load()
        AccelerationStatusEffects().load()
        AccelerationLiquids().load()
        AccelerationBullets().load()
        AccelerationUnits().load()
        AccelerationBlocks().load()
        AccelerationStatusZones().load()
        AccelerationTechTree().load()

        Log.info("Mod [accent]Acceleration[] [sky]Kotlin[] loaded content successfully.")
    }
}