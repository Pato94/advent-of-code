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
            var currentCandidate = nums.average().roundToInt() // avg should be a good estimation
            val fuelUsed = { pos: Int -> nums.sumOf { abs(it - pos) } }
            var updated = true
            while (updated) {
                updated = false
                if (fuelUsed(currentCandidate + 1) < fuelUsed(currentCandidate)) {
                    currentCandidate++
                    updated = true
                }
                if (fuelUsed(currentCandidate - 1) < fuelUsed(currentCandidate)) {
                    currentCandidate--
                    updated = true
                }
            }
            return fuelUsed(currentCandidate)
        }
        return 0
    }

    private fun part2(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day07/input.txt")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val nums = it.trim().split(",").map { it.toInt() }
            var currentCandidate = nums.average().roundToInt() // avg should be a good estimation
            val fuelUsed = { pos: Int -> nums.sumOf { (0..abs(it - pos)).sum() } }
            var updated = true
            while (updated) {
                updated = false
                if (fuelUsed(currentCandidate + 1) < fuelUsed(currentCandidate)) {
                    currentCandidate++
                    updated = true
                }
                if (fuelUsed(currentCandidate - 1) < fuelUsed(currentCandidate)) {
                    currentCandidate--
                    updated = true
                }
            }
            return fuelUsed(currentCandidate)
        }
        return 0
    }
}
