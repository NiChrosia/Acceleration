package acceleration.math

open class Mathm {
    companion object {
        inline fun <reified T : Number> avg(vararg args: Number): T {
            var total = 0f
            var totalCount = 0

            args.forEach { t ->
                totalCount++
                total += t.toFloat()
            }

            val output = (total / totalCount)

            return convert<T>(output)
        }
        
        inline fun <reified T : Number> convert(number: Number): T {
            return when(T::class) {
                Int::class -> number.toInt() as T
                Float::class -> number.toFloat() as T
                Double::class -> number.toDouble() as T
                Short::class -> number.toInt().toShort() as T
                Long::class -> number.toLong() as T

                else -> throw ArithmeticException("Unsupported Number Type: ${T::class}")
            }
        }
    }
}