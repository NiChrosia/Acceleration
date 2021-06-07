package acceleration.ui

import arc.graphics.Color
import arc.scene.ui.layout.Cell
import arc.scene.ui.layout.Table

fun Table.line(pad: Float, height: Float, color: Color): Cell<*> {
    return image().growX().pad(pad).padLeft(0f).padRight(0f).height(height).color(color)
}

fun Table.row(index: Int, start: Int = 0) {
    if (index > start) row()
}