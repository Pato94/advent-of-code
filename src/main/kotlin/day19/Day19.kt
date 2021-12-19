package day19

import readAllText
import java.util.*
import kotlin.math.abs

class Day19 {

    fun main() {
        println("Solution for Day 19, part 1 is: ${part1()}")
        println("Solution for Day 19, part 2 is: ${part2()}")
    }

    class ScannerMap {
        val points = mutableListOf<IntArray>()

        fun addPoint(array: IntArray) {
            points.add(array)
        }

        fun rotations(): List<List<IntArray>> {
            val totalRots = listOf(
                intArrayOf(-1, -2, 3),
                intArrayOf(-1, 2, -3),
                intArrayOf(1, -2, -3),
                intArrayOf(1, 2, 3),
                intArrayOf(-1, -3, -2),
                intArrayOf(-1, 3, 2),
                intArrayOf(1, -3, 2),
                intArrayOf(1, 3, -2),
                intArrayOf(-2, -1, -3),
                intArrayOf(-2, 1, 3),
                intArrayOf(2, -1, 3),
                intArrayOf(2, 1, -3),
                intArrayOf(-2, -3, 1),
                intArrayOf(-2, 3, -1),
                intArrayOf(2, -3, -1),
                intArrayOf(2, 3, 1),
                intArrayOf(-3, -1, 2),
                intArrayOf(-3, 1, -2),
                intArrayOf(3, -1, -2),
                intArrayOf(3, 1, 2),
                intArrayOf(-3, -2, -1),
                intArrayOf(-3, 2, 1),
                intArrayOf(3, -2, 1),
                intArrayOf(3, 2, -1)
            )

            return totalRots.map { rot ->
                points.map { point ->
                    val value = { i: Int -> (if (rot[i] >= 0) 1 else -1) * point[abs(rot[i]) - 1] }
                    intArrayOf(value(0), value(1), value(2))
                }
            }
        }

        fun hasPoint(intArray: IntArray): Boolean {
            for (p in points) {
                if (p[0] == intArray[0] && p[1] == intArray[1] && p[2] == intArray[2]) {
                    return true
                }
            }
            return false
        }
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day19/input.txt")
        val scannerMaps = mutableListOf<ScannerMap>()
        val headerRegex = Regex("--- scanner \\d+ ---")
        var currentScannerMap: ScannerMap? = null
        string.lines().forEach { line ->
            if (line.isBlank()) return@forEach

            if (headerRegex.matches(line)) {
                ScannerMap().let {
                    currentScannerMap = it
                    scannerMaps.add(it)
                }
                return@forEach
            }
            val array = line.split(",").mapNotNull { it.toIntOrNull() }.toIntArray()
            currentScannerMap!!.addPoint(array)
        }
        println("Read ${scannerMaps.size} maps")

        val pendingMaps = LinkedList(scannerMaps)
        var merging = true
        outer@ while (pendingMaps.isNotEmpty() && merging) {
            merging = false
            for (i in pendingMaps.indices) {
                for (j in pendingMaps.indices) {
                    if (i >= j) continue
                    for (rot in pendingMaps[j].rotations()) {
                        for (p0 in pendingMaps[i].points) {
                            for (p1 in rot) {
                                val offsetX = p0[0] - p1[0]
                                val offsetY = p0[1] - p1[1]
                                val offsetZ = p0[2] - p1[2]
                                var matches = 0
                                for (e in rot) {
                                    val sum = intArrayOf(e[0] + offsetX, e[1] + offsetY, e[2] + offsetZ)
                                    if (pendingMaps[i].hasPoint(sum)) {
                                        matches++
                                    }
                                }
                                if (matches >= 12) {
                                    for (e in rot) {
                                        val sum = intArrayOf(e[0] + offsetX, e[1] + offsetY, e[2] + offsetZ)
                                        if (!pendingMaps[i].hasPoint(sum)) {
                                            pendingMaps[i].addPoint(sum)
                                        }
                                    }
                                    pendingMaps.removeAt(j)
                                    merging = true
                                    continue@outer
                                }
                            }
                        }
                    }
                }
            }
        }
        println()
        return scannerMaps[0].points.size
    }
}

private fun part2(): Int {
    val string = readAllText("./src/main/kotlin/day19/input.txt")
    val scannerMaps = mutableListOf<Day19.ScannerMap>()
    val headerRegex = Regex("--- scanner \\d+ ---")
    var currentScannerMap: Day19.ScannerMap? = null
    string.lines().forEach { line ->
        if (line.isBlank()) return@forEach

        if (headerRegex.matches(line)) {
            Day19.ScannerMap().let {
                currentScannerMap = it
                scannerMaps.add(it)
            }
            return@forEach
        }
        val array = line.split(",").mapNotNull { it.toIntOrNull() }.toIntArray()
        currentScannerMap!!.addPoint(array)
    }
    println("Read ${scannerMaps.size} maps")

    val pendingMaps = LinkedList(scannerMaps.withIndex().toList())
    val distanceToZero = Array(scannerMaps.size) { IntArray(3) }
    var merging = true
    outer@ while (pendingMaps.isNotEmpty() && merging) {
        merging = false
        for (i in pendingMaps.indices) {
            for (j in pendingMaps.indices) {
                if (i >= j) continue
                val (originalIndex, mapJ) = pendingMaps[j]
                val (_, mapI) = pendingMaps[i]
                for (rot in mapJ.rotations()) {
                    for (p0 in mapI.points) {
                        for (p1 in rot) {
                            val offsetX = p0[0] - p1[0]
                            val offsetY = p0[1] - p1[1]
                            val offsetZ = p0[2] - p1[2]
                            var matches = 0
                            for (e in rot) {
                                val sum = intArrayOf(e[0] + offsetX, e[1] + offsetY, e[2] + offsetZ)
                                if (mapI.hasPoint(sum)) {
                                    matches++
                                }
                            }
                            if (matches >= 12) {
                                for (e in rot) {
                                    val sum = intArrayOf(e[0] + offsetX, e[1] + offsetY, e[2] + offsetZ)
                                    if (!mapI.hasPoint(sum)) {
                                        mapI.addPoint(sum)
                                    }
                                }
                                distanceToZero[originalIndex] = intArrayOf(offsetX, offsetY, offsetZ)
                                pendingMaps.removeAt(j)
                                merging = true
                                continue@outer
                            }
                        }
                    }
                }
            }
        }
    }
    var maxDistance = Int.MIN_VALUE
    for (i in distanceToZero) {
        for (j in distanceToZero) {
            val distance = abs(i[0] - j[0]) + abs(i[1] - j[1]) + abs(i[2] - j[2])
            if (distance > maxDistance) {
                maxDistance = distance
            }
        }
    }
    return maxDistance
}
