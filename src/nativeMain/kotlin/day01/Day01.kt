package day01

import readAllText

class Day01 {
    fun main() {
        println("Solution for Day 01, part 1 is: ${part1()}")
        println("Solution for Day 02, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/nativeMain/kotlin/problem01/input.txt")
        var prev = -1
        var count = 0
        string.lines().forEach {
            if (it.isBlank()) return@forEach
            val depth = it.toInt()
            if (prev in 0 until depth) count++
            prev = depth
        }
        return count
    }

    private fun part2(): Int {
        val string = readAllText("./src/nativeMain/kotlin/problem01/input.txt")
        var a = -1
        var b = -1
        var c = -1
        var count = 0

        string.lines().forEach {
            if (it.isBlank()) return@forEach
            val depth = it.toInt()
            if (a < 0) {
                a = depth
                return@forEach
            }
            if (b < 0) {
                b = depth
                return@forEach
            }
            if (c < 0) {
                c = depth
                return@forEach
            }
            if (a + b + c < b + c + depth) count++
            a = b
            b = c
            c = depth
        }

        return count
    }

}
