package org.task2.numericmethods.lab2

import kotlin.math.sqrt

interface EvaluateQuadratureForm {

    fun evaluate(a: Double, b: Double): Double

    abstract class Abstract(
        protected val function: (Double) -> Double,
    ) : EvaluateQuadratureForm {
        private val evaluatedValues: MutableMap<Double, Double> = mutableMapOf()
        protected fun findEvaluated(value: Double): Double {
            return evaluatedValues[value] ?: function(value).also {
                evaluatedValues[value] = it
            }
        }
    }

    class LeftRectangle(
        private val function: (Double) -> Double,
    ) : EvaluateQuadratureForm {
        override fun evaluate(a: Double, b: Double): Double {
            val c1: Double = (b - a)
            val result: Double = c1 * function(a)
            return result
        }
    }

    class Trapezoid(
        function: (Double) -> Double,
    ) : Abstract(function) {
        override fun evaluate(a: Double, b: Double): Double {
            val c: Double = (b - a)
            val f1: Double = findEvaluated(a)
            val f2: Double = findEvaluated(b)
            val result: Double = c * ((f1 + f2) / 2.0)
            return result
        }
    }

    class Simpson(
        function: (Double) -> Double,
    ) : Abstract(function) {
        override fun evaluate(a: Double, b: Double): Double {
            val f1: Double = findEvaluated(a)
            val f2: Double = findEvaluated((a + b) / 2.0)
            val f3: Double = findEvaluated(b)
            val result: Double = (b - a) / 6.0 * (f1 + 4.0 * f2 + f3)
            return result
        }
    }

    class Gauss(
        private val function: (Double) -> Double,
    ) : EvaluateQuadratureForm {
        override fun evaluate(a: Double, b: Double): Double {
            val x1 = (a + b) / 2.0 + (b - a) / 2.0 * (-sqrt(3.0 / 5.0))
            val x2 = (a + b) / 2.0
            val x3 = (a + b) / 2.0 + (b - a) / 2.0 * (sqrt(3.0 / 5.0))
            val f1 = function(x1)
            val f2 = function(x2)
            val f3 = function(x3)
            val result = (b - a) / 18.0 * (5.0 * f1 + 8.0 * f2 + 5.0 * f3)
            return result
        }
    }
}