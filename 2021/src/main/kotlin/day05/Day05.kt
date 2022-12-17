package day05

import kotlin.math.abs
import readAllText
import kotlin.math.max
import kotlin.math.sign


class Day05 {

    fun main() {
        println("Solution for Day 05, part 1 is: ${part1()}")
        println("Solution for Day 05, part 2 is: ${part2()}")
    }

    private fun addPointsToArray1(array: Array<IntArray>, x1: Int, y1: Int, x2: Int, y2: Int) {
        if (x1 == x2 || y1 == y2) {
            addPointsToArray2(array, x1, y1, x2, y2)
        }
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day05/input.txt")
        val array = Array(1000) { IntArray(1000) { 0 } }
        val regex = Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val (a, b, c, d) = regex.matchEntire(it)!!.destructured
            addPointsToArray1(array, a.toInt(), b.toInt(), c.toInt(), d.toInt())
        }

        var answer = 0
        for (v in array) {
            for (i in v) {
                if (i > 1) answer++
            }
        }
        return answer
    }

    private fun addPointsToArray2(array: Array<IntArray>, x1: Int, y1: Int, x2: Int, y2: Int) {
        val dx = x2 - x1
        val dy = y2 - y1
        for (i in 0..max(abs(dx), abs(dy))) {
            array[x1 + i * dx.sign][y1 + i * dy.sign]++
        }
    }

    private fun part2(): Int {
        val string = readAllText("./src/main/kotlin/day05/input.txt")
        val array = Array(1000) { IntArray(1000) { 0 } }
        val regex = Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val (a, b, c, d) = regex.matchEntire(it)!!.destructured
            addPointsToArray2(array, a.toInt(), b.toInt(), c.toInt(), d.toInt())
        }

        var answer = 0
        for (v in array) {
            for (i in v) {
                if (i > 1) answer++
            }
        }
        return answer
    }
}
