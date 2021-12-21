package day21

import readAllText
import kotlin.math.max

class Day21 {

    fun main() {
        println("Solution for Day 21, part 1 is: ${part1()}")
        println("Solution for Day 21, part 2 is: ${part2()}")
    }

    class DeterministicDie {
        private var current = 1

        fun roll(): Int {
            val prev = current
            current = (current % 100) + 1
            return prev
        }
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day21/input.txt")
        val playerRegex = Regex("Player (\\d+) starting position: (\\d+)")
        val scores = IntArray(2)
        val position = IntArray(2)
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val (num, start) = playerRegex.matchEntire(it)!!.destructured
            position[num.toInt() - 1] = start.toInt()
        }
        val die = DeterministicDie()
        var currentPlayer = 0
        var rolled = 0
        while (true) {
            val a = die.roll()
            val b = die.roll()
            val c = die.roll()
            rolled += 3
            position[currentPlayer] = ((position[currentPlayer] + a + b + c - 1) % 10) + 1
            scores[currentPlayer] += position[currentPlayer]
            if (scores[currentPlayer] >= 1000) {
                break
            }
            currentPlayer = (currentPlayer + 1) % 2
        }
        return scores[(currentPlayer + 1) % 2] * rolled
    }

    // {3=1, 4=3, 5=6, 6=7, 7=6, 8=3, 9=1}
    private val quantumDieResult = arrayOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)

    private fun timesWon(count1Won: Boolean, turn1: Boolean, pos1: Int, score1: Int, pos2: Int, score2: Int): Long {
        var count = 0L
        for ((sum, c) in quantumDieResult) {
            if (turn1) {
                val newPos1 = ((pos1 + sum - 1) % 10) + 1
                val newScore1 = score1 + newPos1
                if (newScore1 >= 21) {
                    if (count1Won) {
                        count += c
                    }
                } else {
                    count += c * timesWon(count1Won, !turn1, newPos1, newScore1, pos2, score2)
                }
            } else {
                val newPos2 = ((pos2 + sum - 1) % 10) + 1
                val newScore2 = score2 + newPos2
                if (newScore2 >= 21) {
                    if (!count1Won) {
                        count += c
                    }
                } else {
                    count += c * timesWon(count1Won, !turn1, pos1, score1, newPos2, newScore2)
                }
            }
        }
        return count
    }

    private fun part2(): Long {
        val string = readAllText("./src/main/kotlin/day21/input.txt")
        val playerRegex = Regex("Player (\\d+) starting position: (\\d+)")
        val position = IntArray(2)
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val (num, start) = playerRegex.matchEntire(it)!!.destructured
            position[num.toInt() - 1] = start.toInt()
        }
        val times1Won = timesWon(true, true, position[0], 0, position[1], 0)
        val times2Won = timesWon(false, true, position[0], 0, position[1], 0)
        return max(times1Won, times2Won)
    }
}
