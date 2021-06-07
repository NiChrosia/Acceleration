package acceleration.ui.dialogs.modularunit

import acceleration.Acceleration
import acceleration.type.modularunit.ModularUnitModule
import acceleration.type.modularunit.ModularUnitProperties
import arc.Core
import arc.graphics.Color
import arc.math.Mathf
import arc.scene.Element
import arc.scene.event.InputEvent
import arc.scene.event.InputListener
import arc.scene.ui.Dialog
import arc.scene.ui.layout.Table
import mindustry.gen.Icon
import mindustry.gen.Tex
import mindustry.graphics.Pal
import mindustry.ui.Bar
import mindustry.ui.Styles
import mindustry.ui.dialogs.BaseDialog

open class InstallModuleDialog : BaseDialog(Core.bundle.format("dialog.modular-unit-install-module.name")) {
    /** Whether the module exists in the current blueprint. */
    protected var moduleExists: Boolean = false
    protected var module: ModularUnitModule? = null
        set(module) {
            field = module

            rebuild()
        }

    protected var applyAmount = 1
    protected var applyHovered = false
    protected var moduleProperties: ModularUnitProperties? = null

    protected fun rebuild() {
        buttons.clearChildren()
        cont.clearChildren()
        clearListeners()

        this.addCloseListener()
        this.addCloseButton()

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

    open fun show(module: ModularUnitModule): Dialog? {
        // check if modularUnitProperties has it, and if it doesn't, use the new version
        val existingModule = Acceleration.modularUnitProperties.getModule(module.internalName)
        this.module = existingModule ?: module
        moduleExists = existingModule != null

        return show()
    }

    protected fun buildDescription(table: Table) { table.apply {
        module?.let { module ->
            image(module.icon).size(64f, 64f)

            row()

            add(module.name, module.color)

            row()

            add(module.description, Styles.defaultLabel, 0.75f).growX().wrap()

            row()

            val maxLevel = module.max - module.level
            // minimize applyAmount to maxLevel if maxLevel is greater than 1,
            // otherwise make it the max for showing the current amount applied.
            applyAmount = Mathf.clamp(applyAmount, 1, if (maxLevel < 1) module.max else maxLevel)

            if (module.level < module.max) {
                label { "Apply amount: $applyAmount" }.color(Pal.accent).fontScale(0.85f)

                // only show the slider if it can actually modify applyAmount
                if (maxLevel > 1) {
                    slider(1f, (module.max - module.level).toFloat(), 1f, applyAmount.toFloat()) { sliderOutput ->
                        applyAmount = sliderOutput.toInt()
                    }
                }

                row()

                buttons.button("Apply", Icon.ok) {
                    if (moduleExists) {
                        module.apply { level = Mathf.clamp(level + applyAmount, 1, max) }
                    } else Acceleration.modularUnitProperties.add(module.copy().apply { level = applyAmount })

                    hide()
                }.size(160f, 64f).get().addListener(object : InputListener() {
                    /** When the mouse hovers over the apply button */
                    override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Element?) {
                        applyHovered = true

                        // show preview for current properties combined with the properties of this module
                        moduleProperties = module.copy().apply(Acceleration.modularUnitProperties.copy())
                    }

                    /** When the mouse moves off of the apply button */
                    override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Element?) {
                        applyHovered = false
                        moduleProperties = null
                    }
                })
            }
        }
    }}

    protected fun buildProperties(table: Table) { table.apply {
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

    protected fun buildRequirements(table: Table) { table.apply {
        module?.let { module ->
            add("Requirements:", Pal.accent)

            row()

            for (itemstack in module.requirements()) {
                image(itemstack.item.uiIcon)

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