package day10

import readAllText

class Day10 {

    fun main() {
        println("Solution for Day 10, part 1 is: ${part1()}")
        println("Solution for Day 10, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val openingChars = "<([{"
        val closingChars = ">)]}"
        val string = readAllText("./src/nativeMain/kotlin/day10/input.txt")
        var score = 0
        outer@ for (line in string.lines()) {
            if (line.isBlank()) continue

            val stack = ArrayDeque<Char>()
            for (c in line.trim()) {
                when (c) {
                    in openingChars -> stack.addFirst(c)
                    in closingChars -> {
                        if (stack.isEmpty()) {
                            score += scoreOf(c)
                            continue@outer
                        }
                        val complement = stack.removeFirst()
                        val index = closingChars.indexOf(c)
                        if (complement != openingChars[index]) {
                            score += scoreOf(c)
                        }
                    }
                    else -> throw IllegalStateException("Unexpected char $c")
                }
            }
        }
        return score
    }

    private fun scoreOf(char: Char): Int {
        return when (char) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> throw IllegalStateException("Unexpected char $char")
        }
    }

    private fun part2(): Long {
        val openingChars = "<([{"
        val closingChars = ">)]}"
        val string = readAllText("./src/nativeMain/kotlin/day10/input.txt")
        val scores = mutableListOf<Long>()
        outer@ for (line in string.lines()) {
            if (line.isBlank()) continue

            val stack = ArrayDeque<Char>()
            for (c in line.trim()) {
                when (c) {
                    in openingChars -> stack.addFirst(c)
                    in closingChars -> {
                        if (stack.isEmpty()) {
                            continue@outer
                        }
                        val complement = stack.removeFirst()
                        val index = closingChars.indexOf(c)
                        if (complement != openingChars[index]) {
                            continue@outer
                        }
                    }
                    else -> throw IllegalStateException("Unexpected char $c")
                }
            }
            var localScore = 0L
            while (stack.isNotEmpty()) {
                localScore *= 5
                localScore += when (stack.removeFirst()) {
                    '[' -> 2
                    '(' -> 1
                    '{' -> 3
                    '<' -> 4
                    else -> throw IllegalStateException("Unexpected char")
                }
            }
            scores.add(localScore)
        }
        return scores.sorted()[(scores.size - 1) / 2]
    }
}
