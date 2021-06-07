package acceleration.ui.dialogs.modularunit

import arc.Core
import arc.scene.ui.layout.Table
import mindustry.gen.Icon
import mindustry.gen.Tex
import mindustry.ui.dialogs.BaseDialog

open class BlueprintDialog : BaseDialog(Core.bundle.format("dialog.modular-unit-blueprint.name")) {
    private var searchText = ""

    open fun build() {
        cont.clearChildren()
        buttons.clearChildren()
        clearListeners()

        this.addCloseListener()
        this.addCloseButton()

        cont.table(Tex.button) { main -> main.apply {
            table(Tex.button) { navigation -> navigation.apply {
                buildNavigation(this)
            }}.size(1000f, 200f)

            row()

            table(Tex.button) { blueprints -> blueprints.apply {
                buildBlueprints(this)
            }}.size(1000f, 600f)
        }}.size(1050f, 850f)
    }

    init {
        this.build()
    }

    open fun buildNavigation(table: Table) { table.apply {
        image(Icon.zoom)
        val searchField = field(searchText) { result ->
            searchText = result
        }.get()
        searchField?.update {
            searchField.text = searchText
        }
    }}

    open fun buildBlueprints(table: Table) { table.apply {

    }}
}