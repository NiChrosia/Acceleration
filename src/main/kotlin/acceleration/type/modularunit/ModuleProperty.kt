package acceleration.type.modularunit

import arc.graphics.Color

open class ModuleProperty {
    open var name: String = ""
    open var color: Color = Color.white
    var min: Float = 0f
    var max: Float = 100f
    var value: Float = 0f
    open var fraction: Float = value / max

    open fun update() {
        fraction = value / max
    }
}