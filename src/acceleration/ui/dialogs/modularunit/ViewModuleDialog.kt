package acceleration.ui.dialogs.modularunit

import acceleration.type.modularunit.ModularUnitModule
import arc.Core
import arc.scene.ui.Dialog
import mindustry.ui.dialogs.BaseDialog

open class ViewModuleDialog : BaseDialog(Core.bundle.format("dialog.modular-unit-view-module.name")) {
    private var module: ModularUnitModule? = null

    init {
        this.addCloseListener()
        this.addCloseButton()
    }

    fun show(module: ModularUnitModule): Dialog? {
        this.module = module

        return show()
    }
}