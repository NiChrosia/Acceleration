package acceleration.content
import arc.Core
import arc.scene.ui.SettingsDialog
import mindustry.Vars

class AccelerationSettings {
    private fun addSetting(name: String, default: Boolean) {
        Vars.ui.settings.graphics.checkPref(name, Core.settings.getBool(name, default))
    }

    private fun addSlider(name: String, default: Int, min: Int, max: Int, step: Int, processor: SettingsDialog.StringProcessor) {
        Vars.ui.settings.graphics.sliderPref(name, default, min, max, step, processor)
    }

    /** Needs to be called in ClientLoadEvent */
    fun load() {
        addSetting("animatedstatuszone", true)
        addSetting("statuszoneparticles", true)
        addSetting("animateditems", true)
    }
}