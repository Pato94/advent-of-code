package day17

import readAllText
import kotlin.IllegalStateException

class Day17 {

    fun main() {
        println("Solution for Day 17, part 1 is: ${part1()}")
        println("Solution for Day 17, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day17/input.txt")
        val regex = Regex("target area: x=(\\d+)..(\\d+), y=-(\\d+)..-(\\d+)")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val parsed = regex.matchEntire(it) ?: throw IllegalStateException("Unexpected line $it")
            val (x1, x2, y1, y2) = parsed.destructured
            return findMax1(x1.toInt(), x2.toInt(), y1.toInt() * -1, y2.toInt() * -1)
        }
        return 0
    }

    private fun findMax1(x1: Int, x2: Int, y1: Int, y2: Int): Int {
        val ax = -1
        val ay = -1
        var yMax = Int.MIN_VALUE
        for (dx0 in 0..500) {
            for (dy0 in 0..500) {
                var dx = dx0
                var dy = dy0
                var x = 0
                var y = 0
                var found = false
                var localYMax = Int.MIN_VALUE
                while (!found) {
                    x += dx
                    y += dy
                    if (dx > 0) dx += ax
                    dy += ay
                    if (x > x2) {
                        break
                    }
                    if (y < y1) {
                        break
                    }
                    if (y > localYMax) {
                        localYMax = y
                    }
                    if (x in x1..x2 && y in y1..y2) {
                        found = true
                    }
                }
                if (found) {
                    if (localYMax > yMax) {
                        yMax = localYMax
                    }
                }
            }
        }

        return yMax
    }

    private fun part2(): Int {
        val string = readAllText("./src/main/kotlin/day17/input.txt")
        val regex = Regex("target area: x=(\\d+)..(\\d+), y=-(\\d+)..-(\\d+)")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val parsed = regex.matchEntire(it) ?: throw IllegalStateException("Unexpected line $it")
            val (x1, x2, y1, y2) = parsed.destructured
            return findMax2(x1.toInt(), x2.toInt(), y1.toInt() * -1, y2.toInt() * -1)
        }
        return 0
    }

    private fun findMax2(x1: Int, x2: Int, y1: Int, y2: Int): Int {
        val ax = -1
        val ay = -1
        var total = 0
        for (dx0 in -1000..1000) {
            for (dy0 in -1000..1000) {
                var dx = dx0
                var dy = dy0
                var x = 0
                var y = 0
                var found = false
                var localYMax = Int.MIN_VALUE
                while (!found) {
                    x += dx
                    y += dy
                    if (dx > 0) dx += ax
                    dy += ay
                    if (x > x2) {
                        break
                    }
                    if (y < y1) {
                        break
                    }
                    if (y > localYMax) {
                        localYMax = y
                    }
                    if (x in x1..x2 && y in y1..y2) {
                        found = true
                    }
                }
                if (found) {
                    total++
                }
            }
        }

        return total
    }
}
