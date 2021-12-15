package day02

import readAllText

class Day02 {
    fun main() {
        println("Solution for Day 02, part 1 is: ${part1()}")
        println("Solution for Day 02, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day02/input.txt")
        var x = 0
        var depth = 0

        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val values = it.split(" ")
            assert(values.size == 2) { "Parsing illegal line $it" }

            val keyword = values[0]
            val value = values[1].toInt()

            when(keyword) {
                "forward" -> x += value
                "up" -> depth -= value
                "down" -> depth += value
            }
        }

        return x * depth
    }

    private fun part2(): Int {
        val string = readAllText("./src/main/kotlin/day02/input.txt")
        var x = 0
        var aim = 0
        var depth = 0

        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val values = it.split(" ")
            assert(values.size == 2) { "Parsing illegal line $it" }

            val keyword = values[0]
            val value = values[1].toInt()

            when(keyword) {
                "forward" -> {
                    x += value
                    depth += value * aim
                }
                "up" -> aim -= value
                "down" -> aim += value
            }
        }

        return x * depth
    }
}
