package org.task2.numericmethods

import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.style.Styler
import org.task2.InterpolationPolynomial
import org.task2.Node
import kotlin.math.*
import org.knowm.xchart.SwingWrapper

fun main() {
    task4()
    println("________________________")
    xValuesGraphical.forEach {
        println(String.format("%.6f", it))
    }
    println("________________________")
    yValuesGraphical.forEach {
        println(String.format("%.6f", it))
    }
    println("________________________")
}

var xValuesGraphical: MutableList<Double> = mutableListOf()
var yValuesGraphical: MutableList<Double> = mutableListOf()

fun tabulate(x: Double): Double {
    val n = 20
    var result = 0.0
    var a_i = x
    var current = 0
    while (current < n) {
        a_i = ((-1.0).pow(current)) * (x.pow(2 * current + 1)) /
                ((factorial(current.toLong())) * (2 * current + 1))
        result += a_i
        current += 1
    }
    return result * 2 / (sqrt(Math.PI))
}

fun factorial(n: Long): Long {
    return if (n <= 1) 1 else n * factorial(n - 1)
}


fun task1() {
    val xValues = List(11) { i -> i * 0.2 }
    xValues.forEach {
        println(String.format("%.2f", it) + " " + String.format("%.10f", tabulate(it)))
        yValuesGraphical.add(tabulate(it))
    }
}

fun task2() {
    maxDiff(6)
    showGraphic(
        "значение x",
        "погрешность в точке x"
    )
}

fun task3() {
    for (i in 6..65) {
        xValuesGraphical.add(i.toDouble())
        yValuesGraphical.add(maxDiff(i))
    }
    showGraphic(
        "Количество узлов",
        "Максимальная погрешность"
    )
}

fun task4() {
    maxDiffChebyshev(6)
    showGraphic(
        "Значение x",
        "Погрешность в точке x"
    )
}

fun task5() {

    for (i in 6..40) {
        xValuesGraphical.add(i.toDouble())
        yValuesGraphical.add(maxDiffChebyshev(i, true))
    }
    showGraphic("n узлов", "Максимальная погрешность для n узлов")
}

fun showGraphic(
    xName: String,
    yName: String,
) {
    val chart = XYChartBuilder()
        .width(800)
        .height(600)
        .xAxisTitle(xName)
        .yAxisTitle(yName)
        .build()
    chart.styler.legendPosition = Styler.LegendPosition.InsideNW
    chart.styler.isPlotGridLinesVisible = true

    chart.addSeries("name", xValuesGraphical, yValuesGraphical)

    // Отображение графика
    SwingWrapper(chart).displayChart()
}

fun showDifferences(
    xValues: List<Double>,
    lagrange: InterpolationPolynomial,
    n: Int,
): Double {
    var maxDifference = 0.0
    val yValues: MutableList<Double> = mutableListOf()
    xValues.forEachIndexed { index, it ->
        val calculatedPol = lagrange.calculate(it)
        val xValue = it
        val tabulated = tabulate(it)
        val diff = abs(tabulated - calculatedPol)
        yValues.add(diff)
        maxDifference = max(diff, maxDifference)
        println(
            "${String.format("%.6f", xValue)} ${String.format("%.6f", tabulated)} " +
                    "${String.format("%.6f", calculatedPol)} ${String.format("%.6f", diff)}"
        )
    }
    yValuesGraphical = yValues
    xValuesGraphical = xValues
        .map {
            lagrange.calculate(it)
        }
        .toMutableList()
    println("$n ${String.format("%.13f", maxDifference)}")
    return maxDifference
}

fun maxDiff(
    n: Int,
): Double {
    val xValues = List(((n) * 2 - 1)) { it / (n - 1.0) }
    val lagrange = InterpolationPolynomial.Lagrange(
        ArrayList(
            List(n) { it / ((n - 1.0) / 2.0) }.map {
                Node(it, tabulate(it))
            }
        )
    )
//    println(lagrange)
    return showDifferences(
        xValues,
        lagrange,
        n
    )
}

fun maxDiffChebyshev(
    n: Int,
    task5: Boolean = false,
): Double {
    val innerN = if (task5) 101 else n
    val xValues = List((innerN * 2 - 1).toInt()) { it / (innerN - 1.0) }
    val nodesT = crateChebyshev(Pair(0.0, 2.0), n - 1)
    val lagrange = InterpolationPolynomial.Lagrange(
        ArrayList(
            nodesT.map {
                Node(it, tabulate(it))
            }
        )
    )
    return showDifferences(
        xValues,
        lagrange,
        n
    )
}

fun crateChebyshev(segment: Pair<Double, Double>, count: Int): List<Double> {
    val (a, b) = segment
    val newXValues = List(count + 1) { index ->
        val x = roundToPlaces((a + b) / 2) +
                roundToPlaces((b - a) / 2) *
                cos(roundToPlaces(((2 * index + 1) * PI) / (2 * count + 2)))
        x
    }
    return newXValues
}

private fun roundToPlaces(value: Double, places: Int = 8): Double {
    val factor = 10.0.pow(places.toDouble())
    return round(value * factor) / factor
}