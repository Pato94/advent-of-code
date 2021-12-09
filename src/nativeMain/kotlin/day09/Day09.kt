package day09

import readAllText

class Day09 {

    fun main() {
        println("Solution for Day 09, part 1 is: ${part1()}")
        println("Solution for Day 09, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day09/input.txt")
        val array: Array<IntArray> = string.lines().filter { it.isNotBlank() }.map {
            it.trim().split("").mapNotNull { it.toIntOrNull() }.toIntArray()
        }.toTypedArray()

        var risk = 0
        for (i in array.indices) {
            for (j in array[i].indices) {
                if (isLowPoint(array, i, j)) {
                    risk += array[i][j] + 1
                }
            }
        }
        return risk
    }

    private fun isLowPoint(array: Array<IntArray>, i: Int, j: Int): Boolean {
        return neighbors(array, i, j).all { (k, l) -> array[i][j] < array[k][l] }
    }

    private fun part2(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day09/input.txt")
        val array: Array<IntArray> = string.lines().filter { it.isNotBlank() }.map {
            it.trim().split("").mapNotNull { it.toIntOrNull() }.toIntArray()
        }.toTypedArray()

        val basins = mutableListOf<Set<Pair<Int, Int>>>()
        for (i in array.indices) {
            for (j in array[i].indices) {
                if (isLowPoint(array, i, j)) {
                    basins.add(createBasin(array, i, j))
                }
            }
        }
        return basins.sortedByDescending { it.size }.take(3)
            .foldRight(1) { e, a -> a * e.size }
    }

    private fun neighbors(array: Array<IntArray>, i: Int, j: Int): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()
        if (i > 0) list.add(i - 1 to j)
        if (j < array[i].size - 1) list.add(i to j + 1)
        if (i < array.size - 1) list.add(i + 1 to j)
        if (j > 0) list.add(i to j - 1)
        return list
    }

    private fun createBasin(array: Array<IntArray>, i: Int, j: Int): Set<Pair<Int, Int>> {
        val list = mutableSetOf<Pair<Int, Int>>()
        list.add(i to j)
        val minNeighbors = neighbors(array, i, j).filter { (k, l) -> array[i][j] < array[k][l] && array[k][l] < 9 }
        minNeighbors.forEach { (k, l) ->
            list.addAll(createBasin(array, k, l))
        }
        return list
    }
}
