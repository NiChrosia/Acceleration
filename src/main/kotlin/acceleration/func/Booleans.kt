package acceleration.func

fun Boolean.toInt() = if (this) 1 else 0
fun Boolean.toFloat() = toInt().toFloat()
fun Boolean.toDouble() = toInt().toDouble()

fun Any.toNull(cond: Boolean) = if (cond) this else null