package acceleration.ui.dialogs.modularunit

import acceleration.Acceleration
import acceleration.content.AccelerationUnitTypes
import acceleration.type.modularunit.Blueprint
import acceleration.world.blocks.units.ModularUnitFactory
import arc.Core
import arc.graphics.Color
import arc.scene.Element
import arc.scene.event.InputEvent
import arc.scene.event.InputListener
import arc.scene.ui.Dialog
import arc.scene.ui.Image
import arc.scene.ui.layout.Scl
import arc.scene.ui.layout.Table
import mindustry.Vars
import mindustry.gen.Icon
import mindustry.gen.Tex
import mindustry.ui.Bar
import mindustry.ui.Styles
import mindustry.ui.dialogs.BaseDialog

open class FactoryDialog : BaseDialog("@modular-unit-factory-dialog", Styles.defaultDialog) {
    var blueprint: Blueprint = Blueprint()
    open lateinit var from: ModularUnitFactory.ModularUnitFactoryBuild

    fun show(from: ModularUnitFactory.ModularUnitFactoryBuild): Dialog? {
        this.from = from
        this.blueprint = this.from.blueprint.copy()

        rebuild()

        return show()
    }

    override fun hide() {
        from.blueprint = blueprint.copy()

        super.hide()
    }

    init {
        rebuild()
    }

    fun rebuild() {
        cont.clearChildren()
        buttons.clearChildren()
        clearListeners()

        this.addCloseButton()
        this.addResetButton()

        addExportButton()
        addBlueprintsButton()
        addSpawnButton()

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
        var originalBlueprint = blueprint

        buttons.defaults().size(210f, 64f)

        buttons.button(Core.bundle.format("button.modular-unit-reset.name"), Icon.refresh) {
            blueprint = Blueprint()
        }.name("clear").get().addListener(object : InputListener() {
            /** When the mouse hovers over the apply button */
            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Element?) {
                originalBlueprint = blueprint
                blueprint = Blueprint()
            }

            /** When the mouse moves off of the apply button */
            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Element?) {
                blueprint = originalBlueprint
            }
        })
    }

    /** The button for access to the blueprint area, similar to schematics. */
    protected fun addBlueprintsButton() {
        buttons.button("@blueprints", Icon.edit) {
            Acceleration.ui.blueprints.show(this)
        }.name("blueprints").size(210f, 64f)
    }

    protected fun buildModules(table: Table) { table.apply {
        pane { modules -> modules.apply {
            Acceleration.MUModules.each { module ->
                button({ button -> button.apply {
                    table { icon -> icon.apply {
                        center()
                        image(module.icon).size(32f, 32f).tooltip(module.name)
                        pack()

                        table { installed -> installed.apply {
                            top().right()
                            add(Image(Icon.okSmall)).color(Color.lime.shiftSaturation(module.level * 20f)).size(Scl.scl(12f))

                            // check for module in current blueprint, if it isn't there, it can't be applied.
                            visible { blueprint.hasModule(module) }

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
            blueprint.arr.forEachIndexed { index, prop ->
                add(Bar(prop.name, prop.color) {
                    //blueprint[index].update()
                    blueprint.arr[index].fraction
                }).size(400f, 40f)

                row()
            }
        }}
    }}

    protected fun buildInstalledModules(table: Table) { table.apply {
        clear()

        pane { modules -> modules.apply {
            Acceleration.MUModules.each { module ->
                button({ button -> button.apply {
                    table { icon -> icon.apply {
                        center()
                        image(module.icon).size(32f, 32f).tooltip(module.name)

                        visible { blueprint.hasModule(module) }
                        setDisabled { !blueprint.hasModule(module) }

                        pack()

                        table { numeral -> numeral.apply {
                            top().right()
                            visible { blueprint.hasModule(module) }
                            label { blueprint.getModule(module)?.level.toString() }.fontScale(0.5f)
                        }}
                    }}
                }}, Styles.accenti) {
                    Acceleration.ui.modularUnitViewModule.show(blueprint.getModule(module) ?: module)
                }
            }
        }}.size(400f, 800f).growX().left()
    }}

    protected fun addExportButton() {
        buttons.button("@export", Icon.upload) {
            var moduleName = ""

            val exportBlueprintDialog = BaseDialog("@export-blueprint").apply {
                addCloseButton()

                cont.field("") { moduleName = it }

                buttons.button("@ok", Icon.ok) {
                    Acceleration.blueprints.addBlueprint(blueprint.copy(name = moduleName))

                    Acceleration.ui.blueprints.show(this@FactoryDialog)
                    hide()

                    Acceleration.blueprints.save()
                }.size(210f, 64f)
            }

            exportBlueprintDialog.show()
        }.size(210f, 64f).disabled { blueprint.modules.isEmpty() }
    }

    protected fun addSpawnButton() {
        buttons.button("@spawn", Icon.box) {
            AccelerationUnitTypes.modularUnit.spawn(from.team, from.x, from.y, blueprint)
        }.size(210f, 64f).disabled { !Vars.state.rules.infiniteResources }
    }
}