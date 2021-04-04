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
    }

    companion object {
        lateinit var glacialGlade : SectorPreset
    }
}