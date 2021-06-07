package acceleration.content

import acceleration.ui.line
import arc.Core
import mindustry.Vars
import mindustry.graphics.Pal

open class AccelerationSettings {
    private fun addSetting(name: String, default: Boolean = true) {
        Vars.ui.settings.graphics.checkPref(name, Core.settings.getBool(name, default))
    }

    /** Needs to be called in ClientLoadEvent */
    open fun load() {
        Vars.ui.settings.graphics.line(5f, 5f, Pal.accent)
        addSetting("animated-status-zone")
        addSetting("status-zone-particles")
        addSetting("animated-items")
    }
}