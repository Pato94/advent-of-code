package day18

import readAllText
import java.lang.StringBuilder

class Day18 {

    fun main() {
        println("Solution for Day 18, part 1 is: ${part1()}")
        println("Solution for Day 18, part 2 is: ${part2()}")
    }

    interface SnailNumber {
        var parent: ComposedNumber?
        fun numbers(): List<RegularNumber>
        fun magnitude(): Int
        fun nestedLevel(): Int {
            var curr = parent
            var i = -1
            while (curr != null) {
                curr = curr.parent
                i++
            }
            return i
        }
    }

    data class RegularNumber(val number: Int, override var parent: ComposedNumber?) : SnailNumber {

        override fun magnitude(): Int {
            return number
        }

        override fun numbers(): List<RegularNumber> {
            return listOf(this)
        }

        override fun toString(): String {
            return "$number"
        }
    }

    class ComposedNumber(override var parent: ComposedNumber?) : SnailNumber {
        lateinit var a: SnailNumber
        lateinit var b: SnailNumber

        override fun magnitude(): Int {
            return 3 * a.magnitude() + 2 * b.magnitude()
        }

        override fun numbers(): List<RegularNumber> {
            return listOf(a.numbers(), b.numbers()).flatten()
        }

        fun replaceChild(child: SnailNumber, newChild: SnailNumber) {
            if (a === child) a = newChild
            else if (b === child) b = newChild
        }

        override fun toString(): String {
            return "[$a,$b]"
        }
    }

    private fun parseSnailNumber(string: String, parent: ComposedNumber? = null): SnailNumber {
        val regularNumberRegex = Regex("(\\d+)")
        regularNumberRegex.matchEntire(string)?.let {
            val (x) = it.destructured
            return RegularNumber(x.toInt(), parent)
        }
        val aSb = StringBuilder("")
        var i = 1
        var openBrackets = 0
        for (c in string.substring(i)) {
            if (c == '[') openBrackets++
            if (c == ']') openBrackets--
            if (c == ',' && openBrackets == 0) break
            aSb.append(c)
            i++
        }
        i++ // skip comma
        val bSb = StringBuilder("")
        for (c in string.substring(i, string.length-1)) {
            if (c == '[') openBrackets++
            if (c == ']') openBrackets--
            if (c == ',' && openBrackets == 0) break
            bSb.append(c)
            i++
        }
        val ret = ComposedNumber(
            parent
        )
        val a = parseSnailNumber(aSb.toString(), ret)
        val b = parseSnailNumber(bSb.toString(), ret)
        ret.a = a
        ret.b = b
        return ret
    }

    private fun reduce(snailNumber: SnailNumber): SnailNumber {
        if (snailNumber is RegularNumber) {
            return snailNumber
        }
        if (snailNumber is ComposedNumber) {
            val numbers = snailNumber.numbers()
            var reduced = false
            for (i in numbers.indices) {
                if (numbers[i].nestedLevel() >= 4) {
                    doExplosion(numbers, i)
                    reduced = true
                    break
                }
            }
            if (!reduced) {
                for (a in numbers) {
                    if (a.number >= 10) {
                        doSplit(a)
                        reduced = true
                        break
                    }
                }
            }
            return if (reduced) reduce(snailNumber) else snailNumber
        }
        return snailNumber
    }

    private fun doSplit(a: RegularNumber) {
        val newChild = ComposedNumber(a.parent)
        newChild.a = RegularNumber(a.number / 2, newChild)
        newChild.b = RegularNumber((a.number + 1) / 2, newChild)
        a.parent!!.replaceChild(a, newChild)
    }

    private fun doExplosion(numbers: List<RegularNumber>, i: Int) {
        val a = numbers[i]
        val b = numbers[i + 1]
        if (i > 0) {
            val prevNumber = numbers[i - 1]
            val newNumber = RegularNumber(prevNumber.number + a.number, prevNumber.parent)
            prevNumber.parent!!.replaceChild(prevNumber, newNumber)
        }
        if (i + 2 < numbers.size) {
            val nextNumber = numbers[i + 2]
            val newNumber = RegularNumber(nextNumber.number + b.number, nextNumber.parent)
            nextNumber.parent!!.replaceChild(nextNumber, newNumber)
        }
        val zero = RegularNumber(0, a.parent!!.parent!!)
        a.parent!!.parent!!.replaceChild(a.parent!!, zero)
    }

    private fun join(a: String, b: String): String {
        return "[$a,$b]"
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day18/input.txt")
        var currentSnailNumber: SnailNumber? = null
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val newNum = reduce(parseSnailNumber(it.trim()))
            currentSnailNumber =
                if (currentSnailNumber == null) newNum
                else reduce(parseSnailNumber(join(currentSnailNumber!!.toString(), newNum.toString())))
        }
        return currentSnailNumber?.magnitude() ?: 0
    }

    private fun part2(): Int {
        val string = readAllText("./src/main/kotlin/day18/input.txt")
        val numbers = mutableListOf<SnailNumber>()
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            numbers.add(reduce(parseSnailNumber(it.trim())))
        }
        var max = Int.MIN_VALUE
        for (i in numbers) {
            for (j in numbers) {
                val sumMagnitude = reduce(parseSnailNumber(join(i.toString(), j.toString()))).magnitude()
                if (sumMagnitude > max) {
                    max = sumMagnitude
                }
            }
        }
        return max
    }
}
