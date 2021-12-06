package day05

import readAllText
import kotlin.math.max
import kotlin.math.min


class Day05 {

    fun main() {
        println("Solution for Day 05, part 1 is: ${part1()}")
        println("Solution for Day 05, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day05/input.txt")
        val lines = mutableListOf<Line>()
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val values = it.split(" -> ").flatMap { it.split(",") }.map { it.toInt() }
            assert(values.size == 4) { "Invalid input line $it" }
            lines.add(Line(values[0], values[1], values[2], values[3]))
        }

        val map = mutableMapOf<Pair<Int, Int>, Int>()
        lines.flatMap { it.points() }
            .forEach {
                val currentValue = map.getOrPut(it) { 0 }
                map[it] = currentValue + 1
            }

        var answer = 0
        for ((_, v) in map) {
            if (v > 1) {
                answer++
            }
        }
        return answer
    }

    private fun part2(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day05/input.txt")
        val lines = mutableListOf<Line>()
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val values = it.split(" -> ").flatMap { it.split(",") }.map { it.toInt() }
            assert(values.size == 4) { "Invalid input line $it" }
            lines.add(Line(values[0], values[1], values[2], values[3]))
        }

        val map = mutableMapOf<Pair<Int, Int>, Int>()
        lines.flatMap { it.points2() }
            .forEach {
                val currentValue = map.getOrPut(it) { 0 }
                map[it] = currentValue + 1
            }

        var answer = 0
        for ((_, v) in map) {
            if (v > 1) {
                answer++
            }
        }
        return answer
    }

    data class Line(
        private val x1: Int,
        private val y1: Int,
        private val x2: Int,
        private val y2: Int
    ) {
        fun points2(): List<Pair<Int, Int>> {
            val xs = IntProgression.fromClosedRange(x1, x2, if (x1 > x2) -1 else 1).toList()
            val ys = IntProgression.fromClosedRange(y1, y2, if (y1 > y2) -1 else 1).toList()
            val points = mutableListOf<Pair<Int, Int>>()
            while (points.size < max(xs.size, ys.size)) {
                val i = min(points.size, xs.size - 1)
                val j = min(points.size, ys.size - 1)
                points.add(xs[i] to ys[j])
            }
            return points
        }

        fun points(): List<Pair<Int, Int>> {
            return when {
                x1 == x2 -> {
                    IntProgression.fromClosedRange(y1, y2, if (y1 > y2) -1 else 1).map { x1 to it }
                }
                y1 == y2 -> {
                    IntProgression.fromClosedRange(x1, x2, if (x1 > x2) -1 else 1).map { it to y1 }
                }
                else -> {
                    emptyList()
                }
            }
        }

        private fun rangeSize(intRange: IntRange): Int {
            return intRange.last - intRange.first + 1
        }
    }
}
