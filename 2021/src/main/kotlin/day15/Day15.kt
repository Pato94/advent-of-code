package day15

import readAllText
import java.util.*
import kotlin.Comparator

class Day15 {

    fun main() {
        println("Solution for Day 15, part 1 is: ${part1()}")
        println("Solution for Day 15, part 2 is: ${part2()}")
    }

    private val dirs = listOf(
        0 to 1,
        1 to 0,
        -1 to 0,
        0 to -1
    )

    private fun h(grid: Array<IntArray>, x: Int, y: Int): Int {
        return (grid.size - 1 - y) + (grid[0].size - 1 - x)
    }

    private fun toKey(x: Int, y: Int): Int {
        return x * 1000 + y
    }

    private fun toX(key: Int): Int {
        return key / 1000
    }

    private fun toY(key: Int): Int {
        return key % 1000
    }

    private fun aStar(grid: Array<IntArray>): Int {
        val gScore = mutableMapOf<Int, Int>()
        gScore[toKey(0, 0)] = 0

        val fScore = mutableMapOf<Int, Int>()
        fScore[toKey(0, 0)] = h(grid, 0, 0)

        val openSet = PriorityQueue(Comparator<Int> { o1, o2 ->
            (fScore[o1] ?: Int.MAX_VALUE) - (fScore[o2] ?: Int.MAX_VALUE)
        })
        openSet.add(toKey(0, 0))

        while (openSet.isNotEmpty()) {
            val currentKey = openSet.remove()
            val currentX = toX(currentKey)
            val currentY = toY(currentKey)
            if (currentX == (grid[0].size - 1) && currentY == (grid.size - 1)) {
                return gScore[currentKey]!!
            }

            for (dir in dirs) {
                val newX = currentX + dir.first
                val newY = currentY + dir.second
                val newKey = toKey(newX, newY)
                if (newX in grid[0].indices && newY in grid.indices) {
                    val tentativeGScore = (gScore[currentKey] ?: Int.MAX_VALUE) + grid[newY][newX]
                    if (tentativeGScore < gScore[newKey] ?: Int.MAX_VALUE) {
                        gScore[newKey] = tentativeGScore
                        fScore[newKey] = tentativeGScore + h(grid, newX, newY)
                        if (newKey !in openSet) {
                            openSet.add(newKey)
                        }
                    }
                }
            }
        }

        throw IllegalStateException("Solution not found")
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day15/input.txt")
        val grid: Array<IntArray> = string.lines().mapNotNull { l ->
            if (l.isBlank()) null
            else l.split("").mapNotNull { it.toIntOrNull() }.toIntArray()
        }.toTypedArray()

        return aStar(grid)
    }

    private fun part2(): Int {
        val string = readAllText("./src/main/kotlin/day15/input.txt")
        val grid: Array<IntArray> = string.lines().mapNotNull { l ->
            if (l.isBlank()) null
            else l.split("").mapNotNull { it.toIntOrNull() }.toIntArray()
        }.toTypedArray()
        val realGrid = Array(grid.size * 5) { IntArray(grid[0].size * 5) }
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                realGrid[i][j] = grid[i][j]
            }
        }
        for (i in 0..4) {
            for (j in 0..4) {
                for (k in grid.indices) {
                    for (l in grid[k].indices) {
                        val newK = i * grid.size + k
                        val newL = j * grid[0].size + l
                        val newElem = (grid[k][l] + i + j - 1) % 9 + 1
                        realGrid[newK][newL] = newElem
                    }
                }
            }
        }
        return aStar(realGrid)
    }

}
