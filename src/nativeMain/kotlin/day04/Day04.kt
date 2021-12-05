package day04

import readAllText

class Board(
    val n1: Int, val n2: Int, val n3: Int, val n4: Int, val n5: Int,
    val n6: Int, val n7: Int, val n8: Int, val n9: Int, val n10: Int,
    val n11: Int, val n12: Int, val n13: Int, val n14: Int, val n15: Int,
    val n16: Int, val n17: Int, val n18: Int, val n19: Int, val n20: Int,
    val n21: Int, val n22: Int, val n23: Int, val n24: Int, val n25: Int
) {
    private val numbers = mutableListOf<Int>()

    fun markNumber(number: Int) {
        numbers.add(number)
    }

    fun sumUnmarkedNumbers(): Int {
        return listOf(
            listOf(n1, n2, n3, n4, n5),
            listOf(n6, n7, n8, n9, n10),
            listOf(n11, n12, n13, n14, n15),
            listOf(n16, n17, n18, n19, n20),
            listOf(n21, n22, n23, n24, n25)
        ).map { it.filter { it !in numbers } }.sumOf { it.sum() }
    }

    fun winnerLine(): List<Int>? {
        return listOf(
            listOf(n1, n2, n3, n4, n5),
            listOf(n6, n7, n8, n9, n10),
            listOf(n11, n12, n13, n14, n15),
            listOf(n16, n17, n18, n19, n20),
            listOf(n21, n22, n23, n24, n25),
            listOf(n1, n6, n11, n16, n21),
            listOf(n2, n7, n12, n17, n22),
            listOf(n3, n8, n13, n18, n23),
            listOf(n4, n9, n14, n19, n24),
            listOf(n5, n10, n15, n20, n25)
        ).firstOrNull { it.all(numbers::contains) }
    }

    companion object {
        fun fromList(list: List<Int>): Board {
            return Board(
                list[0], list[1], list[2], list[3], list[4],
                list[5], list[6], list[7], list[8], list[9],
                list[10], list[11], list[12], list[13], list[14],
                list[15], list[16], list[17], list[18], list[19],
                list[20], list[21], list[22], list[23], list[24]
            )
        }
    }
}

class Day04 {
    fun main() {
        println("Solution for Day 04, part 1 is: ${part1()}")
        println("Solution for Day 04, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day04/input.txt")
        val lines = string.lines()

        val numbers = lines[0].split(",").map { it.toInt() }
        val boards = mutableListOf<Board>()

        var currentNumbers = mutableListOf<Int>()
        lines.drop(2).forEach {
            if (it.isBlank()) return@forEach

            currentNumbers.addAll(it.split(" ").mapNotNull { it.toIntOrNull() })
            if (currentNumbers.size == 25) {
                boards.add(Board.fromList(currentNumbers))
                currentNumbers = mutableListOf()
            }
        }

        numbers.forEach { number ->
            boards.forEach { it.markNumber(number) }
            val maybeSum = boards.firstOrNull { it.winnerLine() != null }?.sumUnmarkedNumbers()
            if (maybeSum != null) {
                return maybeSum * number
            }
        }

        return 0
    }

    private fun part2(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day04/input.txt")
        val lines = string.lines()

        val numbers = lines[0].split(",").map { it.toInt() }
        val boards = mutableListOf<Board>()

        var currentNumbers = mutableListOf<Int>()
        lines.drop(2).forEach {
            if (it.isBlank()) return@forEach

            currentNumbers.addAll(it.split(" ").mapNotNull { it.toIntOrNull() })
            if (currentNumbers.size == 25) {
                boards.add(Board.fromList(currentNumbers))
                currentNumbers = mutableListOf()
            }
        }

        var currentIndex = 0
        loop@ while (boards.size > 1) {
            while (currentIndex < numbers.size) {
                val number = numbers[currentIndex]
                boards.forEach { it.markNumber(number) }
                val winners = boards.filter { it.winnerLine() != null }
                for (w in winners) {
                    if (boards.size == 1) {
                        currentIndex++
                        break@loop
                    }
                    boards.remove(w)
                }
                currentIndex++
            }
        }

        val pendingBoard = boards.first()
        while (currentIndex < numbers.size && pendingBoard.winnerLine() == null) {
            pendingBoard.markNumber(numbers[currentIndex])
            currentIndex++
        }

        return pendingBoard.sumUnmarkedNumbers() * numbers[currentIndex - 1]
    }
}
