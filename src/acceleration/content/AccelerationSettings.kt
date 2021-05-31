package acceleration.content
import arc.Core
import arc.scene.ui.SettingsDialog
import mindustry.Vars

open class AccelerationSettings {
    private fun addSetting(name: String, default: Boolean = true) {
        Vars.ui.settings.graphics.checkPref(name, Core.settings.getBool(name, default))
    }

    /** Needs to be called in ClientLoadEvent */
    open fun load() {
        addSetting("animated-status-zone")
        addSetting("status-zone-particles")
        addSetting("animated-items")
    }
}