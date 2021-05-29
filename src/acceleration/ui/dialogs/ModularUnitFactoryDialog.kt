package acceleration.ui.dialogs

import acceleration.Acceleration
import acceleration.type.modularunit.ModularUnitModules
import arc.Core
import arc.scene.ui.layout.Table
import mindustry.gen.Icon
import mindustry.gen.Tex
import mindustry.ui.Bar
import mindustry.ui.Styles
import mindustry.ui.dialogs.BaseDialog

class ModularUnitFactoryDialog : BaseDialog(Core.bundle.format("dialog.modular-unit-factory.name"), Styles.defaultDialog) {
    init {
        addCloseListener()

        buttons.button("@back", Icon.left) { hide() }.name("back").size(160f, 64f)

        cont.table(Tex.button) { table -> table.apply {
                table(Tex.button) { modules -> modules.apply {
                    buildModules(this)
                }}.size(500f, 800f).left()

                table { table -> table.apply {
                    table(Tex.button) { properties -> properties.apply {
                        buildProperties(this)
                    }}.size(500f, 400f).top()

                    row()

                    table(Tex.button) { installedModules -> installedModules.apply {
                        buildInstalledModules(this)
                    }}.size(500f, 400f).bottom()
                }}.size(500f, 800f).right()
            }
        }.size(1050f, 850f)
    }

    private fun buildModules(table: Table) { table.apply {
        pane { modules -> modules.apply {
            ModularUnitModules.each { module ->
                button({ button -> button.apply {
                    image(module.icon).size(32f, 32f).tooltip(module.name)
                }}, Styles.accenti) {
                    Acceleration.ui.modularUnitModule.show(module)
                }
            }
        }}.size(400f, 800f).growX().left()
    }}

    private fun buildProperties(table: Table) { table.apply {
        Acceleration.modularUnitProperties.each { prop ->
            add(Bar(prop.name, prop.color) {
                prop.fraction
            }).size(400f, 40f)

            row()
        }
    }}

    private fun buildInstalledModules(table: Table) { table.apply {

    }}
}