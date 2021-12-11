package day11

import readAllText

class Day11 {

    fun main() {
        println("Solution for Day 11, part 1 is: ${part1()}")
        println("Solution for Day 11, part 2 is: ${part2()}")
    }

    private fun step(grid: Array<IntArray>): Int {
        var totalFlashes = 0
        for (row in grid) {
            for (i in row.indices) {
                row[i]++
            }
        }
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] > 9) {
                    totalFlashes += doFlash(grid, i, j)
                }
            }
        }
        return totalFlashes
    }

    private val neighbors = listOf(
        -1 to -1,
        -1 to 0,
        -1 to 1,
        0 to -1,
        0 to 1,
        1 to -1,
        1 to 0,
        1 to 1
    )

    private fun doFlash(grid: Array<IntArray>, i: Int, j: Int): Int {
        var flashes = 1
        grid[i][j] = 0
        for (n in neighbors) {
            val nI = i + n.first
            val nJ = j + n.second
            if (nI !in grid.indices) continue
            if (nJ !in grid[i].indices) continue
            if (grid[nI][nJ] > 0) {
                grid[nI][nJ]++
            }
            if (grid[nI][nJ] > 9) {
                flashes += doFlash(grid, nI, nJ)
            }
        }
        return flashes
    }

    private fun dumpGrid(grid: Array<IntArray>) {
        for (row in grid) {
            println(row.joinToString())
        }
    }

    private fun part1(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day11/input.txt")
        val grid: Array<IntArray> = string.lines().mapNotNull { l ->
            if (l.isBlank()) null
            else l.split("").mapNotNull { it.toIntOrNull() }.toIntArray()
        }.toTypedArray()

        var totalFlashes = 0
        for (i in 1..100) {
            totalFlashes += step(grid)
        }
        return totalFlashes
    }

    private fun part2(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day11/input.txt")
        val grid: Array<IntArray> = string.lines().mapNotNull { l ->
            if (l.isBlank()) null
            else l.split("").mapNotNull { it.toIntOrNull() }.toIntArray()
        }.toTypedArray()

        var i = 0
        while (true) {
            i++
            if (step(grid) == 100) {
                return i
            }
        }
    }
}
