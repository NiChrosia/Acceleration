package acceleration.ui.dialogs

import arc.Core
import arc.util.Scaling
import mindustry.gen.Icon
import mindustry.gen.Tex
import mindustry.ui.Styles
import mindustry.ui.dialogs.BaseDialog

class ModularUnitFactoryDialog : BaseDialog("modular-unit-factory", Styles.defaultDialog) {
    init {
        addCloseListener()

        buttons.button("@back", Icon.left) { hide() }.name("back").size(160f, 64f)

        cont.table(Tex.button) { table -> table.apply {
                table(Tex.button) { table ->
                    table.apply {
                        pane {
                            it.button(
                                { b -> b.image(Core.atlas.find("")).size(42f).scaling(Scaling.fit) },
                                Styles.clearTransi
                            ) {

                            }.size(48f).pad(3f)
                        }.size(200f, 400f).growX().left()
                    }
                }.size(250f, 500f).left().pad(10f)

                table { table ->
                    table.apply {
                        table(Tex.button) {}.size(250f, 250f).top().right().pad(10f)

                        row()

                        table(Tex.button) {}.size(250f, 250f).bottom().right().pad(10f)
                    }
                }.size(250f, 500f).right().pad(10f)
            }
        }.size(550f, 550f)
    }
}