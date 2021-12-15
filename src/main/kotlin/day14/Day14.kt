package day14

import readAllText

class Day14 {

    fun main() {
        println("Solution for Day 14, part 1 is: ${part1()}")
        println("Solution for Day 14, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day14/input.txt")
        val ruleRegex = Regex("(\\w+) -> (\\w+)")
        var start = ""
        val rules = mutableMapOf<String, String>()
        var count = mutableMapOf<String, Int>()
        string.lines().forEach { line ->
            if (line.isBlank()) return@forEach
            if (start.isEmpty()) {
                start = line.trim()
                for (i in 0..start.length - 2) {
                    val pair = start.substring(i, i + 2)
                    count[pair] = (count[pair] ?: 0) + 1
                }
                return@forEach
            }

            val result = ruleRegex.matchEntire(line)
            if (result != null) {
                val (pair, new) = result.destructured
                rules[pair] = new
            }
        }
        repeat(10) {
            val newCount = mutableMapOf<String, Int>()
            for ((k, v) in count) {
                val newElem = rules[k]!!
                val newPair1 = k[0] + newElem
                val newPair2 = newElem + k[1]
                newCount[newPair1] = (newCount[newPair1] ?: 0) + v
                newCount[newPair2] = (newCount[newPair2] ?: 0) + v
            }
            count = newCount
        }
        val sums = count.flatMap { (k, v) -> listOf(k[0] to v, k[1] to v) }.groupBy { it.first }.map { it.key to it.value.map { it.second }.sum() }
        // We're counting double
        val max = (sums.maxByOrNull { it.second }!!.second + 1) / 2
        val min = (sums.minByOrNull { it.second }!!.second + 1) / 2
        return max - min
    }

    private fun part2(): Long {
        val string = readAllText("./src/main/kotlin/day14/input.txt")
        val ruleRegex = Regex("(\\w+) -> (\\w+)")
        var start = ""
        val rules = mutableMapOf<String, String>()
        var count = mutableMapOf<String, Long>()
        string.lines().forEach { line ->
            if (line.isBlank()) return@forEach
            if (start.isEmpty()) {
                start = line.trim()
                for (i in 0..start.length - 2) {
                    val pair = start.substring(i, i + 2)
                    count[pair] = (count[pair] ?: 0) + 1
                }
                return@forEach
            }

            val result = ruleRegex.matchEntire(line)
            if (result != null) {
                val (pair, new) = result.destructured
                rules[pair] = new
            }
        }
        repeat(40) {
            val newCount = mutableMapOf<String, Long>()
            for ((k, v) in count) {
                val newElem = rules[k]!!
                val newPair1 = k[0] + newElem
                val newPair2 = newElem + k[1]
                newCount[newPair1] = (newCount[newPair1] ?: 0) + v
                newCount[newPair2] = (newCount[newPair2] ?: 0) + v
            }
            count = newCount
        }
        val sums = count.flatMap { (k, v) -> listOf(k[0] to v, k[1] to v) }.groupBy { it.first }.map { it.key to it.value.map { it.second }.sum() }
        // We're counting double
        val max = (sums.maxByOrNull { it.second }!!.second + 1) / 2
        val min = (sums.minByOrNull { it.second }!!.second + 1) / 2
        return max - min
    }

}
