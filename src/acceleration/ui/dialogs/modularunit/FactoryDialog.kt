package acceleration.ui.dialogs.modularunit

import acceleration.Acceleration
import acceleration.type.modularunit.ModularUnitProperties
import arc.Core
import arc.graphics.Color
import arc.scene.Element
import arc.scene.event.InputEvent
import arc.scene.event.InputListener
import arc.scene.ui.Image
import arc.scene.ui.layout.Scl
import arc.scene.ui.layout.Table
import mindustry.gen.Icon
import mindustry.gen.Tex
import mindustry.ui.Bar
import mindustry.ui.Styles
import mindustry.ui.dialogs.BaseDialog

open class FactoryDialog : BaseDialog(Core.bundle.format("dialog.modular-unit-factory.name"), Styles.defaultDialog) {
    protected var moduleProperties = Acceleration.modularUnitProperties

    init {
        rebuild()
    }

    fun rebuild() {
        this.addCloseListener()
        this.addCloseButton()
        this.addResetButton()

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
    
    protected fun addResetButton() {
        buttons.defaults().size(210f, 64f)
        buttons.button(Core.bundle.format("button.modular-unit-reset.name"), Icon.refresh) {
            Acceleration.modularUnitProperties = ModularUnitProperties()
            moduleProperties = Acceleration.modularUnitProperties
        }.name("clear").get().addListener(object : InputListener() {
            /** When the mouse hovers over the apply button */
            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Element?) {
                moduleProperties = ModularUnitProperties()
            }

            /** When the mouse moves off of the apply button */
            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Element?) {
                moduleProperties = Acceleration.modularUnitProperties
            }
        })
    }

    protected fun buildModules(table: Table) { table.apply {
        pane { modules -> modules.apply {
            Acceleration.modularUnitModules.each { module ->
                button({ button -> button.apply {
                    table { icon -> icon.apply {
                        center()
                        image(module.icon).size(32f, 32f).tooltip(module.name)
                        pack()

                        table { installed -> installed.apply {
                            top().right()
                            add(Image(Icon.okSmall)).color(Color.lime.shiftSaturation(module.level * 20f)).size(Scl.scl(12f))

                            // check for module in current blueprint, if it isn't there, it can't be applied.
                            visible { Acceleration.modularUnitProperties.getModule(module.internalName) != null }

                            pack()
                        }}
                    }}
                }}, Styles.accenti) {
                    Acceleration.ui.modularUnitInstallModule.show(module)
                }
            }
        }}.size(400f, 800f).growX().left()
    }}

    protected fun buildProperties(table: Table) { table.apply {
        clear()

        table { bars -> bars.apply {
            moduleProperties.eachIndexed { index, prop ->
                add(Bar(prop.name, prop.color) {
                    moduleProperties[index].fraction
                }).size(400f, 40f)

                row()
            }
        }}
    }}

    protected fun buildInstalledModules(table: Table) { table.apply {
        clear()

        pane { modules -> modules.apply {
            Acceleration.modularUnitModules.each { module ->
                button({ button -> button.apply {
                    table { icon -> icon.apply {
                        center()
                        image(module.icon).size(32f, 32f).tooltip(module.name)

                        visible { Acceleration.modularUnitProperties.hasModule(module) }
                        setDisabled { !Acceleration.modularUnitProperties.hasModule(module) }

                        pack()

                        table { numeral -> numeral.apply {
                            top().right()
                            label {Acceleration.modularUnitProperties.getModule(module)?.level?.toString() ?: ""}.fontScale(0.5f)

                            visible { Acceleration.modularUnitProperties.hasModule(module) }
                            setDisabled { !Acceleration.modularUnitProperties.hasModule(module) }
                        }}
                    }}
                }}, Styles.accenti) {
                    Acceleration.ui.modularUnitViewModule.show(Acceleration.modularUnitProperties.getModule(module) ?: module)
                }
            }
        }}.size(400f, 800f).growX().left()
    }}
}