package day13

import readAllText

class Day13 {

    fun main() {
        println("Solution for Day 13, part 1 is: ${part1()}")
        println("Solution for Day 13, part 2 is: ${part2()}")
    }

    private fun foldY(array: Array<BooleanArray>, value: Int) {
        for (y in value until array.size) {
            val newY = value * 2 - y
            if (newY >= 0) {
                for (x in array[y].indices) {
                    array[newY][x] = array[newY][x] || array[y][x]
                }
            }
            for (x in array[y].indices) {
                array[y][x] = false
            }
        }
    }

    private fun foldX(array: Array<BooleanArray>, value: Int) {
        for (x in value until array[0].size) {
            val newX = value * 2 - x
            if (newX >= 0) {
                for (y in array.indices) {
                    array[y][newX] = array[y][newX] || array[y][x]
                }
            }
            for (y in array.indices) {
                array[y][x] = false
            }
        }
    }

    private fun dumpArray(array: Array<BooleanArray>, x: Int, y: Int) {
        for (i in 0..y) {
            println(array[i].take(x).map { if (it) "#" else "." }.joinToString(""))
        }
    }

    private fun part1(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day13/input.txt")
        val pointRegex = Regex("(\\d+),(\\d+)")
        val foldRegex = Regex("fold along (\\w+)=(\\d+)")
        val array = Array(2000) { BooleanArray(2000) }
        var folded = false
        string.lines().forEach {
            if (it.isBlank()) return@forEach
            if (folded) return@forEach

            val maybePoint = pointRegex.matchEntire(it)
            if (maybePoint != null) {
                val (x, y) = maybePoint.destructured
                array[y.toInt()][x.toInt()] = true
            } else {
                val (coord, num) = foldRegex.matchEntire(it)!!.destructured
                if (coord == "y") {
                    foldY(array, num.toInt())
                } else {
                    foldX(array, num.toInt())
                }
                folded = true
            }
        }
        var totalPoints = 0
        for (y in array.indices) {
            for (x in array[y].indices) {
                if (array[y][x]) {
                    totalPoints++
                }
            }
        }
        return totalPoints
    }

    private fun part2(): Int {        val string = readAllText("./src/nativeMain/kotlin/day13/input.txt")
        val pointRegex = Regex("(\\d+),(\\d+)")
        val foldRegex = Regex("fold along (\\w+)=(\\d+)")
        val array = Array(2000) { BooleanArray(2000) }
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val maybePoint = pointRegex.matchEntire(it)
            if (maybePoint != null) {
                val (x, y) = maybePoint.destructured
                array[y.toInt()][x.toInt()] = true
            } else {
                val (coord, num) = foldRegex.matchEntire(it)!!.destructured
                if (coord == "y") {
                    foldY(array, num.toInt())
                } else {
                    foldX(array, num.toInt())
                }
            }
        }
        dumpArray(array, 39, 5)
        return 0
    }
}
