package org.task2.numericmethods.lab2

interface EvaluateIntegral {
    fun evaluate(x: Double, n: Long): Double
    class Base(
        private val evaluateQuadratureForm: EvaluateQuadratureForm,
    ) : EvaluateIntegral {
        override fun evaluate(x: Double, n: Long): Double {
            val range: Double = x / n
            var result = 0.0
            for (i in 0..<n) {
                val xi: Double = i * range
                result += evaluateQuadratureForm.evaluate(xi, xi + range)
            }
            return result
        }
    }
}