package acceleration.world.blocks.units

import acceleration.Acceleration
import acceleration.type.modularunit.Blueprint
import arc.graphics.Color
import arc.scene.ui.layout.Table
import mindustry.gen.Building
import mindustry.world.Block
import mindustry.gen.Icon
import mindustry.ui.Styles

open class ModularUnitFactory(name: String) : Block(name) {
    init {
        super.init()

        solid = true
        update = true
        destructible = true
        configurable = true
        hasItems = true
    }

    @Suppress("unused")
    inner class ModularUnitFactoryBuild : Building() {
        lateinit var blueprint: Blueprint

        override fun buildConfiguration(table: Table) {
            table.button(Icon.pencil, Styles.clearTransi) {
                if (!this::blueprint.isInitialized) blueprint = Blueprint()

                Acceleration.ui.modularUnitFactory.show(this)
            }.size(40f).color(Color.lightGray)
        }
    }
}