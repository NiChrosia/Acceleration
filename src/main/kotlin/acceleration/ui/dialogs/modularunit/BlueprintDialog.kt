package acceleration.ui.dialogs.modularunit

import acceleration.Acceleration
import acceleration.func.ui.line
import arc.scene.ui.layout.Table
import mindustry.gen.Icon
import mindustry.gen.Tex
import mindustry.graphics.Pal
import mindustry.ui.dialogs.BaseDialog

open class BlueprintDialog : BaseDialog("@blueprint-dialog") {
    protected var searchText = ""
    protected lateinit var factoryDialog: FactoryDialog
    protected lateinit var navigation: Table
    protected lateinit var blueprints: Table

    open fun show(factoryDialog: FactoryDialog) {
        this.factoryDialog = factoryDialog

        blueprints.apply(this@BlueprintDialog::rebuildBlueprints)
    }

    open fun build() {
        cont.clearChildren()
        buttons.clearChildren()
        clearListeners()

        this.addCloseButton()

        cont.table(Tex.button) { main -> main.apply {
            navigation = table(Tex.button, this@BlueprintDialog::buildNavigation).size(1000f, 200f).get()

            row()

            blueprints = table(Tex.button, this@BlueprintDialog::rebuildBlueprints).size(1000f, 600f).get()
        }}.size(1050f, 850f)
    }

    init {
        this.build()
    }

    open fun buildNavigation(table: Table) { table.apply {
        image(Icon.zoom)
        val searchField = field(searchText) { result ->
            searchText = result

            blueprints.apply(this@BlueprintDialog::rebuildBlueprints)
        }.get()

        searchField?.update {
            searchField.text = searchText
        }
    }}

    open fun rebuildBlueprints(table: Table) { table.apply {
        clearChildren()

        Acceleration.blueprints.all.copy().filter { it.name.contains(searchText, true) }.forEachIndexed { index, blueprint ->
            if (index % 6 == 0) row()

            button({ button -> button.apply {
                add(blueprint.name, Pal.accent)
                line(5f, 5f, Pal.accent, true)
            }}) {
                hide()

                factoryDialog.blueprint = blueprint
            }.size(150f).pad(5f)
        }
    }}
}