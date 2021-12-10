package day07

import platform.posix.abs
import readAllText
import kotlin.math.roundToInt

class Day07 {

    fun main() {
        println("Solution for Day 07, part 1 is: ${part1()}")
        println("Solution for Day 07, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day07/input.txt")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val nums = it.trim().split(",").map { it.toInt() }
            var currentCandidate = nums.sorted()[(nums.size - 1) / 2] // mean should be a good estimation
            val fuelUsed = { pos: Int -> nums.sumOf { abs(it - pos) } }
            return localMin(currentCandidate, fuelUsed)
        }
        return 0
    }

    private fun part2(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day07/input.txt")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val nums = it.trim().split(",").map { it.toInt() }
            val currentCandidate = nums.average().roundToInt() // avg should be a good estimation
            val fuelUsed = { pos: Int ->
                nums.sumOf {
                    val n = abs(it - pos)
                    n * (n + 1) / 2
                }
            }
            return localMin(currentCandidate, fuelUsed)
        }
        return 0
    }

    private fun localMin(start: Int, f: (Int) -> Int): Int {
        var current = start
        var updated = true
        while (updated) {
            updated = false
            if (f(current + 1) < f(current)) {
                current++
                updated = true
            }
            if (f(current - 1) < f(current)) {
                current--
                updated = true
            }
        }
        return f(current)
    }
}
