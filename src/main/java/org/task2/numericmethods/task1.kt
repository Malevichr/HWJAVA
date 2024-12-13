package org.task2.numericmethods

import org.task2.InterpolationPolynomial
import org.task2.Node
import kotlin.math.*

fun tabulate(x: Double): Double {
    val n = 15
    var result = 0.0
    var a_i = x
    var current = 0
    while (current != n) {
        a_i = ((-1).toDouble().pow(current)) * (x.pow(2 * current + 1)) / ((factorial(current)) * (2 * current + 1))
        result += a_i
        current += 1
    }
    return result * 2 / (sqrt(Math.PI))
}

fun factorial(n: Int): Int {
    return if (n <= 1) 1 else n * factorial(n - 1)
}

fun main() {
//    for (i in 6..100 step 2) {
//        maxDiff(i)
//    }
    println("______________________________________________________")
    for (i in 2..50 step 2) {
        maxDiffChebyshev(i)
    }
}

fun showDifferences(
    xValues: List<Double>,
    lagrange: InterpolationPolynomial,
    n: Int,
) {
    var maxDifference = 0.0
    xValues.forEachIndexed { index, it ->
        val calculatedPol = lagrange.calculate(it)
        val xValue = it
        val tabulated = tabulate(it)
        val diff = abs(tabulated - calculatedPol)
        maxDifference = max(diff, maxDifference)
//        println("$index ${String.format("%.10f", xValue)} ${String.format("%.10f", tabulated)} ${String.format("%.10f", calculatedPol)} ${String.format("%.10f", diff)}")
    }
    println("Max difference = ${String.format("%.16f", maxDifference)} , for n = $n")
}

fun maxDiff(
    n: Int,
) {
    val xValues = List(((n - 1.0) * 2).toInt()) { it / (n - 1.0) }
    val lagrange = InterpolationPolynomial.Lagrange(
        ArrayList(
            List(n) { it / ((n - 1.0) / 2.0) }.map {
                Node(it, tabulate(it))
            }
        )
    )
    showDifferences(
        xValues,
        lagrange,
        n
    )
}

fun maxDiffChebyshev(
    n: Int,
) {
    val xValues = List(((50 - 1.0) * 2).toInt()) { it / (50 - 1.0) }
    val nodesT = crateChebyshev(Pair(0.0, 2.0), n)
    val lagrange = InterpolationPolynomial.Lagrange(
        ArrayList(
            nodesT.map {
                Node(it, tabulate(it))
            }
        )
    )
    showDifferences(
        xValues,
        lagrange,
        n
    )
}

fun crateChebyshev(segment: Pair<Double, Double>, count:Int): List<Double> {
    val (a, b) = segment
    val newXValues = List(count + 1) { index ->
        val x = roundToPlaces((a + b) / 2) + roundToPlaces((b - a) / 2) * cos(roundToPlaces(((2 * index + 1) * PI) / (2 * count + 2)))
        x
    }
    return newXValues
}

private fun roundToPlaces(value: Double, places: Int = 4): Double {
    val factor = 10.0.pow(places.toDouble())
    return round(value * factor) / factor
}