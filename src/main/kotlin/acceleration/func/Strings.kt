package acceleration.func

import arc.util.Strings

fun String.stripIf(cond: Boolean): String {
    return if (cond) Strings.stripColors(this) else this
}

fun String.stripColors(): String {
    return Strings.stripColors(this)
}