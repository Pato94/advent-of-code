package day06

import readAllText

class Day06 {

    fun main() {
        println("Solution for Day 06, part 1 is: ${part1()}")
        println("Solution for Day 06, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day06/input.txt")
        val arraySize = 9
        val fishPerDay = IntArray(arraySize) { 0 }

        string.lines().forEach {
            if (it.isBlank()) return@forEach

            string.split(",").map { it.trim().toInt() }.forEach {
                fishPerDay[it] += 1
            }
            var i = 0
            while(i < 80) {
                var prevValue = 0
                for (j in (arraySize-1) downTo 0) {
                    val aux = fishPerDay[j]
                    fishPerDay[j] = prevValue
                    prevValue = aux
                }
                fishPerDay[6] += prevValue
                fishPerDay[8] += prevValue
                i++
            }

            return fishPerDay.sum()
        }
        return 0
    }

    private fun part2(): Long {
        val string = readAllText("./src/main/kotlin/day06/input.txt")
        val arraySize = 9
        val fishPerDay = LongArray(arraySize) { 0 }

        string.lines().forEach {
            if (it.isBlank()) return@forEach

            string.split(",").map { it.trim().toInt() }.forEach {
                fishPerDay[it] += 1.toLong()
            }
            var i = 0
            while(i < 256) {
                var prevValue: Long = 0
                for (j in (arraySize-1) downTo 0) {
                    val aux = fishPerDay[j]
                    fishPerDay[j] = prevValue
                    prevValue = aux
                }
                fishPerDay[6] += prevValue
                fishPerDay[8] += prevValue
                i++
            }

            return fishPerDay.sum()
        }
        return 0
    }
}
