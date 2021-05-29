package acceleration.type.modularunit

import arc.graphics.Color

open class ModularUnitProperty {
    open var name: String = ""
    open var color: Color = Color.white
    open var min: Float = 0f
    open var max: Float = 100f
    open var value: Float = 0f
    open var fraction: Float = value / max

    open fun update() {
        fraction = value / max
    }
}