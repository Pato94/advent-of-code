package day08

import platform.posix.pow
import readAllText

class Day08 {

    fun main() {
        println("Solution for Day 08, part 1 is: ${part1()}")
        println("Solution for Day 08, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day08/input.txt")
        var ones = 0
        var fours = 0
        var sevens = 0
        var eights = 0
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val digits = it.split("|")[1].trim().split(" ")
            ones += digits.count { it.length == 2 }
            fours += digits.count { it.length == 4 }
            sevens += digits.count { it.length == 3 }
            eights += digits.count { it.length == 7 }
        }
        return ones + fours + sevens + eights
    }

    private fun part2(): Int {
        val string = readAllText("./src/nativeMain/kotlin/day08/input.txt")

        var sum = 0
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val mapping = "abcdefg".toCharArray()

            val input = it.split("|")[0].trim().split(" ")
            val digits = it.split("|")[1].trim().split(" ")

            // find a
            val seven = input.first { it.length == 3 }
            val one = input.first { it.length == 2 }
            val a = seven.first { it !in one }
            setInArray(mapping, 'a', a)

            // find d
            val four = input.first { it.length == 4 }
            val twoFiveOrThree = input.filter { it.length == 5 }
            val lettersInTwoFiveOrThree = twoFiveOrThree
                .flatMap { it.toCharArray().toList() }.toSet()
            val commonLettersInTwoFiveOrTree: List<Char> = lettersInTwoFiveOrThree
                .filter { c -> twoFiveOrThree.all { c in it } }
            val d = commonLettersInTwoFiveOrTree.first { it in four }
            setInArray(mapping, 'd', d)

            // find b
            val b = four.toCharArray().first { it !in one && it != d }
            setInArray(mapping, 'b', b)

            // find f
            val five = twoFiveOrThree.first { b in it }
            val f = one.toCharArray().first { it in five }
            setInArray(mapping, 'f', f)

            // find c
            val c = one.toCharArray().first { it != f }
            setInArray(mapping, 'c', c)

            // find e
            val e = lettersInTwoFiveOrThree
                .first { c -> twoFiveOrThree.count { c in it } == 1 && c != b }
            setInArray(mapping, 'e', e)

            setInArray(mapping, 'g', "abcdefg".first { it !in listOf(a, b, c, d, e, f) } )

            sum += digits.reversed().withIndex().sumOf { (i, word) -> toDigit(mapping, word) * pow(10.toDouble(), i.toDouble()).toInt() }
        }
        return sum
    }

    private fun toDigit(charArray: CharArray, word: String): Int {
        fun hasChars(string: String): Boolean {
            return string.all { word.contains(charArray[it - 'a']) }
        }
        return when {
            hasChars("abcdefg") -> 8
            hasChars("abcefg") -> 0
            hasChars("abdefg") -> 6
            hasChars("abcdfg") -> 9
            hasChars("acdeg") -> 2
            hasChars("acdfg") -> 3
            hasChars("abdfg") -> 5
            hasChars("bcdf") -> 4
            hasChars("acf") -> 7
            hasChars("cf") -> 1
            else -> throw IllegalStateException("Unexpected digit $word, char array ${charArray.joinToString()}")
        }
    }

    private fun setInArray(charArray: CharArray, a: Char, b: Char) {
        charArray[a - 'a'] = b
    }
}
