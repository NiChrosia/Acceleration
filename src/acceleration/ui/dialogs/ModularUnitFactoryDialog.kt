package acceleration.ui.dialogs

import arc.Core
import arc.util.Scaling
import mindustry.gen.Icon
import mindustry.gen.Tex
import mindustry.ui.Styles
import mindustry.ui.dialogs.BaseDialog

class ModularUnitFactoryDialog : BaseDialog("modular-unit-factory", Styles.defaultDialog) {
    init {
        buttons.button("@back", Icon.left) { hide() }.name("back").size(160f, 64f)

        cont.table(Tex.button) { table -> table.apply {
            pane {
                it.button({b -> b.image(Core.atlas.find("")).size(42f).scaling(Scaling.fit)}, Styles.clearTransi) {

                }.size(48f).pad(3f)
            }.growX()
        }}.size(200f, 400f).left().pad(10f)

        cont.table {
            it.table(Tex.button) {}.size(200f, 200f).right().top().pad(10f)
            it.table(Tex.button) {}.size(200f, 200f).right().bottom().pad(10f)
        }.size(250f, 500f).right().pad(10f)
    }
}