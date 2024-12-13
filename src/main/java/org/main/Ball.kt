package org.main

interface Ball {
    fun color(): String
    fun weight(): Int
    fun size(): Int
    abstract class Abstract(
        private val color: String,
        private val weight: Int,
        private val size: Int,
    ) : Ball {
        override fun color(): String = color
        override fun weight(): Int = weight
        override fun size(): Int = size
    }

    abstract class Football(
        private val weight: Int,
        private val size: Int,
    ) : Abstract(
        color = "white-black",
        weight = weight,
        size = size
    ) {
        object Small : Football(
            1, 1
        )

        object Medium : Football(
            2, 2
        )

        object Large : Football(
            3, 3
        )
    }

    object Volleyball : Abstract(
        color = "white-yellow-blue",
        weight = 270,
        size = 21
    )

    object Basketball : Abstract(
        color = "orange",
        weight = 600,
        size = 26
    )
}

fun main() {
    val ball = object : Ball.Abstract(
        "хуй",
        100000,
        1000000000
    ) {}
    println(ball.weight())
}