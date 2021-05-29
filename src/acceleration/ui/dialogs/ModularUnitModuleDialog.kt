package acceleration.ui.dialogs

import acceleration.Acceleration
import acceleration.type.modularunit.ModularUnitModule
import acceleration.type.modularunit.ModularUnitProperties
import acceleration.type.modularunit.ModularUnitProperty
import arc.Core
import arc.graphics.Color
import arc.scene.Element
import arc.scene.event.InputEvent
import arc.scene.event.InputListener
import arc.scene.ui.Dialog
import arc.scene.ui.layout.Table
import mindustry.gen.Icon
import mindustry.gen.Tex
import mindustry.graphics.Pal
import mindustry.ui.Bar
import mindustry.ui.Cicon
import mindustry.ui.Styles
import mindustry.ui.dialogs.BaseDialog
import kotlin.math.min

class ModularUnitModuleDialog : BaseDialog(Core.bundle.format("dialog.modular-unit-module.name")) {
    private var module: ModularUnitModule? = null
        set(module) {
            field = module

            rebuild()
        }

    private var applyAmount = 1
    private var applyHovered = false
    private var moduleProperties: ModularUnitProperties? = null

    private fun rebuild() {
        buttons.clearChildren()
        cont.clearChildren()
        clearListeners()


        addCloseListener()

        buttons.button("@back", Icon.left) { hide() }.name("back").size(160f, 64f)

        cont.table(Tex.button) { main -> main.apply {
            table(Tex.button) { description -> description.apply {
                buildDescription(this)
            }}.size(500f, 800f).left()

            table { invisible -> invisible.apply {
                table(Tex.button) { properties -> properties.apply {
                    buildProperties(this)
                }}.size(500f, 400f).right()

                row()

                table(Tex.button) { requirements -> requirements.apply {
                    buildRequirements(this)
                }}.size(500f, 400f).right()
            }}
        }}.size(1050f, 850f)
    }

    init {
        rebuild()
    }

    fun show(module: ModularUnitModule): Dialog? {
        this.module = module
        this.module?.level = 0

        return show()
    }

    private fun buildDescription(table: Table) { table.apply {
        module?.let { module ->
            image(module.icon).size(64f, 64f)

            row()

            add(module.name, module.color)

            row()

            add(module.description, Styles.defaultLabel, 0.75f).growX().wrap()

            row()

            applyAmount = min(applyAmount, module.max)

            if (module.level < module.max) {
                if (module.max > 1) {
                    label { "Apply amount: $applyAmount" }.color(Pal.accent).fontScale(0.85f)

                    slider(1f, (module.max - module.level).toFloat(), 1f, applyAmount.toFloat()) { sliderOutput ->
                        applyAmount = sliderOutput.toInt()
                    }
                }

                row()

                buttons.button("Apply", Icon.ok) {
                    for (unused in 1..applyAmount) module.apply(Acceleration.modularUnitProperties)

                    hide()
                }.size(160f, 64f).get().addListener(object : InputListener() {
                    override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Element?) {
                        applyHovered = true

                        val modularUnitProperties = mutableListOf<ModularUnitProperty>()

                        Acceleration.modularUnitProperties.each { prop ->
                            modularUnitProperties.add(prop)
                        }

                        moduleProperties = module.modifiers.copy().eachIndexed { index, prop ->
                            prop.fraction = min((prop.value * applyAmount / prop.max) + modularUnitProperties[index].fraction, 1f)
                        }
                    }

                    override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Element?) {
                        applyHovered = false
                        moduleProperties = null
                    }
                })
            }
        }
    }}

    private fun buildProperties(table: Table) { table.apply {
        module?.let { module -> module.modifiers.eachIndexed { index, prop ->
            add(Bar(prop.name, prop.color) {
                val defValue = prop.value * applyAmount / prop.max

                moduleProperties?.let {
                    it[index].fraction + defValue
                } ?: defValue
            }).size(400f, 40f)

            row()
        }}
    }}

    private fun buildRequirements(table: Table) { table.apply {
        module?.let { module ->
            add("Requirements:", Pal.accent)

            row()

            for (itemstack in module.requirements()) {
                image(itemstack.item.icon(Cicon.medium))

                add("${itemstack.amount} ${itemstack.item.localizedName}", Color.white, 0.75f).growX().wrap()

                row()
            }

            add("Stats:", Pal.accent)

            row()

            add("Maximum amount: ${module.max}", 0.75f)

            row()
        }
    }}
}