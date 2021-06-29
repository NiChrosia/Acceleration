package acceleration.func.math

fun avg(vararg x: Float): Float {
    return x.average().toFloat()
}

fun Float.negative(cond: Boolean) = this * if (cond) -1 else 1