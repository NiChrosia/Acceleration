package acceleration.ui.dialogs.modularunit

import acceleration.type.modularunit.ModularUnitModule
import acceleration.ui.line
import acceleration.ui.row
import arc.Core
import arc.graphics.Color
import arc.scene.ui.Dialog
import arc.scene.ui.Image
import arc.scene.ui.layout.Table
import mindustry.gen.Tex
import mindustry.graphics.Pal
import mindustry.ui.Bar
import mindustry.ui.Styles
import mindustry.ui.dialogs.BaseDialog

open class ViewModuleDialog : BaseDialog(Core.bundle.format("dialog.modular-unit-view-module.name"), Styles.defaultDialog) {
    private var module: ModularUnitModule? = null

    init {
        rebuild()
    }

    fun rebuild() {
        buttons.clearChildren()
        cont.clearChildren()

        this.addCloseListener()
        this.addCloseButton()

        cont.table(Tex.button) { main -> main.apply {
            table(Tex.button) { description -> description.apply {
                buildDescription(this)
            }}.size(500f, 600f)

            table(Tex.button) { bars -> bars.apply {
                buildBars(this)
            }}.size(500f, 600f)
        }}.size(1050f, 650f)
    }

    fun show(module: ModularUnitModule): Dialog? {
        this.module = module

        rebuild()

        return show()
    }

    fun buildDescription(table: Table) { table.apply {
        module?.let { module ->
            table { icons ->
                icons.apply {
                    add(Image(module.icon)).size(64f, 64f)
                    add(module.name, module.color, 1.1f)
                }
            }.pad(5f)

            row()

            add(module.description, 0.85f).growX().wrap()

            row()

            table { stats ->
                stats.apply {
                    add("Stats: ", Pal.accent, 1.1f)

                    row()

                    line(5f, 3f, Pal.accent)

                    row()

                    add("Requirements: ", Pal.accent)

                    row()

                    table { requirements ->
                        requirements.apply {
                            module.requirements().forEachIndexed { index, itemstack ->
                                row(index)

                                image(itemstack.item.uiIcon)
                                add(itemstack.item.localizedName, Pal.accent)
                                add(" ${itemstack.amount}", Color.lightGray)
                            }
                        }
                    }.pad(5f)

                    row()

                    line(5f, 3f, Pal.accent)

                    row()

                    table { level -> level.apply {
                        add("Level: ", Pal.accent)
                        add("${module.level}", Color.lightGray)
                    }}.pad(5f)
                }
            }
        }
    }}

    fun buildBars(table: Table) { table.apply {
        module?.let { module ->
            val modifiers = module.modifiers

            modifiers.arr.forEachIndexed { index, prop ->
                row(index)

                add(Bar(prop.name, prop.color) {
                    prop.value * module.level / prop.max
                }).size(400f, 62.5f)
            }
        }
    }}
}