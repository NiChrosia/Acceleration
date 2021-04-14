package acceleration.content

import mindustry.ctype.ContentList
import mindustry.type.SectorPreset

class AccelerationSectors : ContentList {
    override fun load() {
        glacialGlade = object : SectorPreset("glacial-glade", AccelerationPlanets.cryogenia, 5) {
            init {
                captureWave = 15
                difficulty = 1f
                description =
                    "An ancient frozen glade situated in a tranquil zone. Contains abnormally high quantities of cryogem for its location. \nCapture the sector. Harness the resources. Move on."
            }
        }

        cryogenicRavine = object : SectorPreset("cryogenic-ravine", AccelerationPlanets.cryogenia, 50) { // TODO find a better sector, and make the map better
            init {
                captureWave = 35
                difficulty = 4f
                description = "The entryway into the deep depths. Many ruins lie here. Proceed with caution."
            }
        }
    }

    companion object {
        lateinit var glacialGlade : SectorPreset

        lateinit var cryogenicRavine : SectorPreset
    }
}