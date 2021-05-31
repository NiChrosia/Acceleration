package acceleration.ui

import acceleration.ui.dialogs.modularunit.FactoryDialog
import acceleration.ui.dialogs.modularunit.InstallModuleDialog
import acceleration.ui.dialogs.modularunit.ViewModuleDialog

open class AccelerationUI {
    val modularUnitFactory = FactoryDialog()
    val modularUnitInstallModule = InstallModuleDialog()
    val modularUnitViewModule = ViewModuleDialog()
}