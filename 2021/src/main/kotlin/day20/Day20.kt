package day20

import readAllText
import java.lang.StringBuilder
import kotlin.math.max
import kotlin.math.min

class Day20 {

    fun main() {
        println("Solution for Day 20, part 1 is: ${part1()}")
        println("Solution for Day 20, part 2 is: ${part2()}")
    }

    private val canvasSize: Int = 400

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day20/input.txt")
        var canvas = Array(canvasSize) { BooleanArray(canvasSize) }
        var readRules = false
        val rules = BooleanArray(512)
        var width = 0
        var height = 0
        var linesRead = 0
        string.lines().forEach {
            if (it.isBlank()) return@forEach
            val trimmed = it.trim()
            if (!readRules) {
                for ((i, c) in trimmed.withIndex()) {
                    rules[i] = c == '#'
                }
                readRules = true
                return@forEach
            }

            width = trimmed.length
            height = width // assume it's a square
            val start = (canvasSize - width) / 2
            for ((i, c) in trimmed.withIndex()) {
                canvas[start + linesRead][i + start] = c == '#'
            }
            linesRead++
        }

        val first = rules.first()
        val last = first && rules.last()
        repeat(2) {
            val res = enhance(canvas, rules, if ((it+1) % 2 == 0) first else last)
            canvas = res
        }

        var count = 0
        val (xRange, yRange) = relevantRegion(canvas)
        for (i in yRange) {
            for (j in xRange) {
                if (canvas[i][j]) count++
            }
        }
        return count
    }

    private fun printCanvas(canvas: Array<BooleanArray>) {
        val (xRange, yRange) = relevantRegion(canvas)
        for (i in yRange) {
            for (j in xRange) {
                print(if (canvas[i][j]) '#' else '.')
            }
            print('\n')
        }
    }

    private fun enhance(canvas: Array<BooleanArray>, rules: BooleanArray, defaultValue: Boolean): Array<BooleanArray> {
        val enhanced = Array(canvasSize) { BooleanArray(canvasSize) { defaultValue } }
        val xRange = 0 until canvasSize
        val yRange = 0 until canvasSize
        for (i in xRange) {
            for (j in yRange) {
                // region
                val sb = StringBuilder("")
                for (k in -1..1) {
                    for (l in -1..1) {
                        if (i + k in yRange && j + l in xRange) {
                            sb.append(if (canvas[i + k][j + l]) '1' else '0')
                        } else {
                            sb.append(if (defaultValue) '1' else '0')
                        }
                    }
                }
                enhanced[i][j] = rules[sb.toString().toInt(2)]
            }
        }
        return enhanced
    }

    private fun relevantRegion(canvas: Array<BooleanArray>): Pair<IntRange, IntRange> {
        var minX = Int.MAX_VALUE
        var minY = Int.MAX_VALUE
        for (i in 0 until canvasSize) {
            for (j in 0 until canvasSize) {
                if (canvas[i][j]) {
                    minX = min(j, minX)
                    minY = min(i, minY)
                }
            }
        }
        var maxX = Int.MIN_VALUE
        var maxY = Int.MIN_VALUE
        for (i in (0 until canvasSize).reversed()) {
            for (j in (0 until canvasSize).reversed()) {
                if (canvas[i][j]) {
                    maxX = max(j, maxX)
                    maxY = max(i, maxY)
                }
            }
        }
        return minX..maxX to minY..maxY
    }

    private fun part2(): Int {
        val string = readAllText("./src/main/kotlin/day20/input.txt")
        var canvas = Array(canvasSize) { BooleanArray(canvasSize) }
        var readRules = false
        val rules = BooleanArray(512)
        var width = 0
        var height = 0
        var linesRead = 0
        string.lines().forEach {
            if (it.isBlank()) return@forEach
            val trimmed = it.trim()
            if (!readRules) {
                for ((i, c) in trimmed.withIndex()) {
                    rules[i] = c == '#'
                }
                readRules = true
                return@forEach
            }

            width = trimmed.length
            height = width // assume it's a square
            val start = (canvasSize - width) / 2
            for ((i, c) in trimmed.withIndex()) {
                canvas[start + linesRead][i + start] = c == '#'
            }
            linesRead++
        }

        val first = rules.first()
        val last = first && rules.last()
        repeat(50) {
            canvas = enhance(canvas, rules, if ((it+1) % 2 == 0) first else last)
        }

        var count = 0
        val (xRange, yRange) = relevantRegion(canvas)
        for (i in yRange) {
            for (j in xRange) {
                if (canvas[i][j]) count++
            }
        }
        return count
    }
}
