package day03

import readAllText

class Day03 {
    fun main() {
        println("Solution for Day 03, part 1 is: ${part1()}")
        println("Solution for Day 03, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day03/input.txt")
        var totalLines = 0
        val sum = IntArray(string.lines().first().length)

        string.lines().forEach { line ->
            if (line.isBlank()) return@forEach

            totalLines++
            val chars = line.toCharArray()

            chars.forEachIndexed { index, c ->
                sum[index] += c - '0'
            }
        }

        val gammaRateArray = CharArray(sum.size)
        val epsilonRate = CharArray(sum.size)
        sum.forEachIndexed { index, i ->
            if (i > totalLines / 2) {
                gammaRateArray[index] = '1'
                epsilonRate[index] = '0'
            } else {
                gammaRateArray[index] = '0'
                epsilonRate[index] = '1'
            }
        }
        return toDecimal(gammaRateArray) * toDecimal(epsilonRate)
    }

    private fun part2(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day03/input.txt")
        val lines = string.lines()
        var totalLines = 0

        lines.forEach { line ->
            if (line.isBlank()) return@forEach

            totalLines++
        }

        var candidates = (0 until totalLines).toMutableList()
        var currentIndex = 0
        while (candidates.size > 1 && currentIndex < lines[0].length) {
            var localSum = 0
            for (i in candidates) {
                localSum += lines[i].toCharArray()[currentIndex] - '0'
            }
            val mostPopularChar = if (localSum >= (candidates.size + 1) / 2) '1' else '0'
            val nextCandidates = mutableListOf<Int>()
            for (candidate in candidates) {
                val currentChar = lines[candidate][currentIndex]
                if (currentChar == mostPopularChar) {
                    nextCandidates.add(candidate)
                }
            }
            candidates = nextCandidates
            currentIndex++
        }
        val oxygen = toDecimal(lines[candidates.first()].toCharArray())

        candidates = (0 until totalLines).toMutableList()
        currentIndex = 0
        while (candidates.size > 1 && currentIndex < lines[0].length) {
            var localSum = 0
            for (i in candidates) {
                localSum += lines[i].toCharArray()[currentIndex] - '0'
            }
            val leastPopularChar = if (localSum >= (candidates.size + 1) / 2) '0' else '1'
            val nextCandidates = mutableListOf<Int>()
            for (candidate in candidates) {
                val currentChar = lines[candidate][currentIndex]
                if (currentChar == leastPopularChar) {
                    nextCandidates.add(candidate)
                }
            }
            candidates = nextCandidates
            currentIndex++
        }
        val c02 = toDecimal(lines[candidates.first()].toCharArray())

        return oxygen * c02
    }

    private fun toDecimal(gammaRateArray: CharArray): Int {
        return gammaRateArray.joinToString("").toInt(2)
    }
}
