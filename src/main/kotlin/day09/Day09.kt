package day09

import logTime
import readAllText

@Suppress("INTEGER_OVERFLOW")
class Day09 {

    fun main() {
        println("Solution for Day 09, part 1 is: ${part1()}")
        println("Solution for Day 09, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day09/input.txt")
        val array: Array<IntArray> = string.lines().filter { it.isNotBlank() }.map {
            it.trim().split("").mapNotNull { it.toIntOrNull() }.toIntArray()
        }.toTypedArray()

        var risk = 0
        for (i in array.indices) {
            for (j in array[i].indices) {
                if (!isLocalMin(array, i, j)) continue

                risk += array[i][j] + 1
            }
        }
        return risk
    }

    private fun part2(): Int {
        val string = readAllText("./src/main/kotlin/day09/input.txt")
        val array: Array<IntArray> = string.lines().filter { it.isNotBlank() }.map {
            it.trim().split("").mapNotNull { it.toIntOrNull() }.toIntArray()
        }.toTypedArray()

        // kotlin native don't have heaps
        val sizes = IntArray(3)

        for (i in array.indices) {
            for (j in array[i].indices) {
                if (!isLocalMin(array, i, j)) continue

                val basinSize = basinSize(array, i, j)

                var minIndex = 0
                for (k in sizes.indices) {
                    if (sizes[k] < sizes[minIndex]) {
                        minIndex = k
                    }
                }
                if (basinSize > sizes[minIndex]) {
                    sizes[minIndex] = basinSize
                }
            }
        }
        return sizes[0] * sizes[1] * sizes[2]
    }

    private fun isLocalMin(array: Array<IntArray>, i: Int, j: Int): Boolean {
        if (i > 0 && array[i][j] >= array[i - 1][j]) return false
        if (j < array[i].size - 1 && array[i][j] >= array[i][j + 1]) return false
        if (i < array.size - 1 && array[i][j] >= array[i + 1][j]) return false
        if (j > 0 && array[i][j] >= array[i][j - 1]) return false
        return true
    }

    private fun basinSize(array: Array<IntArray>, i: Int, j: Int): Int {
        val basin = Array(array.size) { BooleanArray(array[0].size) }
        storeInBasin(array, basin, i, j)
        var basinSize = 0
        for (k in basin.indices) {
            for (l in basin[0].indices) {
                if (basin[k][l]) basinSize++
            }
        }
        return basinSize
    }

    private fun storeInBasin(array: Array<IntArray>, basin: Array<BooleanArray>, i: Int, j: Int) {
        basin[i][j] = true
        if (i > 0 && array[i][j] < array[i - 1][j] && array[i - 1][j] < 9 && !basin[i - 1][j]) {
            storeInBasin(array, basin, i - 1, j)
        }
        if (j < array[i].size - 1 && array[i][j] < array[i][j + 1] && array[i][j + 1] < 9 && !basin[i][j + 1]) {
            storeInBasin(array, basin, i, j + 1)
        }
        if (i < array.size - 1 && array[i][j] < array[i + 1][j] && array[i + 1][j] < 9 && !basin[i + 1][j]) {
            storeInBasin(array, basin, i + 1, j)
        }
        if (j > 0 && array[i][j] < array[i][j - 1] && array[i][j - 1] < 9 && !basin[i][j - 1]) {
            storeInBasin(array, basin, i, j - 1)
        }
    }
}
