package org.task2.numericmethods.lab2

import org.task2.numericmethods.tabulate
import kotlin.math.*

fun main(){
    printResults(EvaluateQuadratureForm.LeftRectangle{ E.pow(-(it*it))}, "Left Rectangle")
    println()
    printResults(EvaluateQuadratureForm.Trapezoid{ E.pow(-(it*it))}, "Trapezoid")
    println()
    printResults(EvaluateQuadratureForm.Gauss{ E.pow(-(it*it))}, "Gauss")
    println()
    printResults(EvaluateQuadratureForm.Simpson{ E.pow(-(it*it))}, "Simpson")
    println()
}

fun printResults(quadratureForm: EvaluateQuadratureForm, tag: String) {
    println(tag)
    val headers = listOf("Xi", "Tabulated", "Sn", "Error", "N")
    val rows = mutableListOf<Row>()
    val range = 0.2
    val evaluateIntegral = EvaluateIntegral.Base(
        quadratureForm
    )
    for (i in 0..10) {
        var n: Long = 1
        val xi = i * range
        val tabulated = tabulate(xi)
        var error = 100000.0
        var sn = 0.0
        while (error > 10.0.pow(-6)) {
            n *= 2
            sn = evaluateIntegral.evaluate(xi, n) * 2.0 / (sqrt(Math.PI))
            error = abs(sn - tabulated)
        }
        rows.add(Row(
            xi,
            tabulated,
            sn,
            error,
            n
        ))
    }


    // Преобразуем данные в строки
    val dataStrings = rows.map { row ->
        listOf(
            "%.1f".format(row.xi),
            "%.10f".format(row.tabulated),
            "%.10f".format(row.sn),
            "%.10f".format(row.error),
            row.n.toString()
        )
    }

    // Определяем ширину каждого столбца
    val columnWidths = List(headers.size) { index ->
        (listOf(headers[index]) + dataStrings.map { it[index] }).maxOf { it.length }
    }

    // Функция для форматирования строки
    fun formatRow(row: List<String>): String {
        return row.mapIndexed { index, cell ->
            cell.padEnd(columnWidths[index])
        }.joinToString(" | ", "| ", " |")
    }

    // Вывод таблицы
    println(formatRow(headers)) // Заголовки
    println("-".repeat(columnWidths.sum() + columnWidths.size * 3 + 1)) // Разделитель
    dataStrings.forEach { println(formatRow(it)) } // Данные
}

data class Row(
    val xi: Double,
    val tabulated: Double,
    val sn: Double,
    val error: Double,
    val n: Long,
)