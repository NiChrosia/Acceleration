package acceleration.math

open class Mathm {
    open fun avg(vararg args: Int): Int {
        var total = 0
        var totalCount = 0
        for (i in args) {
            totalCount++
            total += i
        }

        return total / totalCount
    }

    open fun avg(vararg args: Float): Float {
        var total = 0f
        var totalCount = 0
        for (i in args) {
            totalCount++
            total += i
        }

        return total / totalCount
    }

    open fun avg(vararg args: Double): Double {
        var total = 0.0
        var totalCount = 0
        for (i in args) {
            totalCount++
            total += i
        }

        return total / totalCount
    }
}