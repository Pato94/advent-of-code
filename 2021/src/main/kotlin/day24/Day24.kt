package day24

import readAllText
import kotlin.math.pow

class Day24 {

    fun main() {
        println("Solution for Day 24, part 1 is: ${part1()}")
        println("Solution for Day 24, part 2 is: ${part2()}")
    }

    interface Instruction {
        fun execute(state: MutableMap<String, String>)
    }

    interface Value {
        fun get(state: Map<String, String>): String
    }

    data class LiteralValue(val value: Long) : Value {
        override fun get(state: Map<String, String>): String {
            return value.toString()
        }
    }

    data class Variable(val name: String) : Value {
        override fun get(state: Map<String, String>): String {
            return state[name] ?: "0"
        }
    }

    data class Input(val variable: Variable) : Instruction {
        override fun execute(state: MutableMap<String, String>) {
            state["Variables"] = ((state["Variables"] ?: "0").toInt() + 1).toString()
            state[variable.name] = "i${state["Variables"]}"
        }
    }

    data class Add(val variable: Variable, val value: Value) : Instruction {
        override fun execute(state: MutableMap<String, String>) {
            val a = variable.get(state)
            val b = value.get(state)
            val i = a.toIntOrNull()
            val j = b.toIntOrNull()
            if (b == "0") {
                state[variable.name] = a
            } else if (a == "0") {
                state[variable.name] = b
            } else if (i != null && j != null) {
                state[variable.name] = (i + j).toString()
            } else {
                state[variable.name] = "($a+$b)"
            }
        }
    }

    data class Mul(val variable: Variable, val value: Value) : Instruction {
        override fun execute(state: MutableMap<String, String>) {
            val a = variable.get(state)
            val b = value.get(state)
            if (a == "0") {
                state[variable.name] = "0"
            } else if (b == "0") {
                state[variable.name] = "0"
            } else if (a == "1") {
                state[variable.name] = b
            } else if (b == "1") {
                state[variable.name] = a
            } else {
                val i = a.toIntOrNull()
                val j = b.toIntOrNull()
                if (i != null && j != null) {
                    state[variable.name] = (i * j).toString()
                } else {
                    state[variable.name] = "($a*$b)"
                }
            }
        }
    }

    data class Div(val variable: Variable, val value: Value) : Instruction {
        override fun execute(state: MutableMap<String, String>) {
            if (variable.get(state) == "0") {
                state[variable.name] = "0"
                return
            }
            if (value.get(state) != "1") {
                state[variable.name] = "(${variable.get(state)}/${value.get(state)})"
            }
        }
    }

    data class Mod(val variable: Variable, val value: Value) : Instruction {
        override fun execute(state: MutableMap<String, String>) {
            val a = variable.get(state)
            val b = value.get(state)
            val i = a.toIntOrNull()
            val j = b.toIntOrNull()
            if (i != null && j != null) {
                state[variable.name] = (i % j).toString()
            } else {
                state[variable.name] = "($a%$b)"
            }
        }
    }

    data class Eql(val variable: Variable, val value: Value) : Instruction {
        override fun execute(state: MutableMap<String, String>) {
            val a = variable.get(state)
            val b = value.get(state)
            if (a == b) {
                state[variable.name] = "1"
                return
            }
            if (b.startsWith("i")) {
                val j = a.toIntOrNull()
                if (j == null) {
                    val last = a.substring(1, a.length - 1).split("+").last()
                    val i = last.toIntOrNull()
                    if (i != null && i > 9) {
                        state[variable.name] = "0"
                        return
                    }
                } else {
                    if (j > 9) {
                        state[variable.name] = "0"
                        return
                    }
                    if (j < 0) {
                        state[variable.name] = "0"
                        return
                    }
                }
            }
            val end = "1 else 0)"
            if (a.endsWith(end) && b == "0") {
                state[variable.name] = a.replaceRange(a.length - end.length, a.length, "0 else 1)")
                return
            }
            state[variable.name] = "if ($a==$b) 1 else 0)"
        }
    }

    private fun part1(): Long {
        val string = readAllText("./src/main/kotlin/day24/input.txt")
        val instructions = mutableListOf<Instruction>()
        val state = mutableMapOf<String, String>()
        val inpRegex = Regex("inp (.*)")
        val addRegex = Regex("add (.*) (.*)")
        val mulRegex = Regex("mul (.*) (.*)")
        val divRegex = Regex("div (.*) (.*)")
        val modRegex = Regex("mod (.*) (.*)")
        val eqlRegex = Regex("eql (.*) (.*)")
        string.lines().forEach { line ->
            if (line.isBlank()) return@forEach

            val inpResult = inpRegex.matchEntire(line)
            val addResult = addRegex.matchEntire(line)
            val mulResult = mulRegex.matchEntire(line)
            val divResult = divRegex.matchEntire(line)
            val modResult = modRegex.matchEntire(line)
            val eqlResult = eqlRegex.matchEntire(line)
            when {
                inpResult != null -> {
                    val (a) = inpResult.destructured
                    instructions += Input(Variable(a))
                }
                addResult != null -> {
                    val (a, b) = getValues(addResult)
                    instructions += Add(a, b)
                }
                mulResult != null -> {
                    val (a, b) = getValues(mulResult)
                    instructions += Mul(a, b)
                }
                divResult != null -> {
                    val (a, b) = getValues(divResult)
                    instructions += Div(a, b)
                }
                modResult != null -> {
                    val (a, b) = getValues(modResult)
                    instructions += Mod(a, b)
                }
                eqlResult != null -> {
                    val (a, b) = getValues(eqlResult)
                    instructions += Eql(a, b)
                }
                else -> {
                    throw IllegalStateException("Unexpected instruction $line")
                }
            }
        }
//        for (i in instructions) {
//            i.execute(state)
//        }
//        println(state["z"])
        // Hardcoded input
//        for (i in 1..9) {
//            println("For input $i")
//            copiedFun(i * 10.toDouble().pow(13).toLong())
//        }
        var evaluated = 0
        for (i in (10.toDouble().pow(13).toLong() until 10.toDouble().pow(14).toLong()).reversed()) {
            if (evaluated % 10000000 == 0) {
                println("$evaluated evaluated")
            }
            if (copiedFun2(i)) {
                return i
            }
            evaluated++
        }
        return -1
    }

    private fun getValues(inpResult: MatchResult): Pair<Variable, Value> {
        val (a, b) = inpResult.destructured
        val variable = Variable(a)
        val maybeLong = b.toLongOrNull()
        val value: Value = if (maybeLong != null) {
            LiteralValue(maybeLong)
        } else {
            Variable(b)
        }
        return Pair(variable, value)
    }

    private fun copiedFun2(num: Long): Boolean {
        val string = num.toString().toCharArray()
        if (string.size != 14) throw java.lang.IllegalStateException("Expected number with 14 digits, given $num")
        if (string.any { it == '0' }) return false
        var counter = 13
        fun input(): Long = (string[13-counter--] - '0').toLong()

        fun eql(a: Long, b: Long): Long = if (a == b) 1 else 0

        var w = 0L
        var x = 0L
        var y = 0L
        var z = 0L
        val i1 = input()
        val i2 = input()
        val i3 = input()
        val i4 = input()
        val i5 = input()
        z = (((((((((i1+2)*26)+(i2+4))*26)+(i3+8))*26)+(i4+7))*26)+(i5+12))
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -14
        x += -14
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 7
        y += 7
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x 0
        x += 0
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 10
        y += 10
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 1
        z /= 1
        //add x 10
        x += 10
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 14
        y += 14
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -10
        x += -10
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 2
        y += 2
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 1
        z /= 1
        //add x 13
        x += 13
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 6
        y += 6
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -12
        x += -12
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 8
        y += 8
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -3
        x += -3
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 11
        y += 11
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -11
        x += -11
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 5
        y += 5
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -2
        x += -2
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 11
        y += 11
        //mul y x
        y *= x
        //add z y
        z += y
        return z == 0L
    }

    private fun copiedFun(num: Long): Boolean {
        val string = num.toString().toCharArray()
        if (string.size != 14) throw java.lang.IllegalStateException("Expected number with 14 digits, given $num")
//        if (string.any { it == '0' }) return false
        var counter = 13
        fun input(): Long = (string[13-counter--] - '0').toLong()

        fun eql(a: Long, b: Long): Long = if (a == b) 1 else 0

        var w = 0L
        var x = 0L
        var y = 0L
        var z = 0L
        //inp w
        w = input()
        //mul x 0 Useful?
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 1
        z /= 1
        //add x 10
        x += 10
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 2
        y += 2
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 1
        z /= 1
        //add x 10
        x += 10
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 4
        y += 4
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 1
        z /= 1
        //add x 14
        x += 14
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 8
        y += 8
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 1
        z /= 1
        //add x 11
        x += 11
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 7
        y += 7
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 1
        z /= 1
        //add x 14
        x += 14
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 12
        y += 12
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -14
        x += -14
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 7
        y += 7
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x 0
        x += 0
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 10
        y += 10
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 1
        z /= 1
        //add x 10
        x += 10
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 14
        y += 14
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -10
        x += -10
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 2
        y += 2
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 1
        z /= 1
        //add x 13
        x += 13
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 6
        y += 6
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -12
        x += -12
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 8
        y += 8
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -3
        x += -3
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 11
        y += 11
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -11
        x += -11
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 5
        y += 5
        //mul y x
        y *= x
        //add z y
        z += y
        //inp w
        w = input()
        //mul x 0
        x *= 0
        //add x z
        x += z
        //mod x 26
        x %= 26
        //div z 26
        z /= 26
        //add x -2
        x += -2
        //eql x w
        x = eql(x, w)
        //eql x 0
        x = eql(x, 0)
        //mul y 0
        y *= 0
        //add y 25
        y += 25
        //mul y x
        y *= x
        //add y 1
        y += 1
        //mul z y
        z *= y
        //mul y 0
        y *= 0
        //add y w
        y += w
        //add y 11
        y += 11
        //mul y x
        y *= x
        //add z y
        z += y
        return z == 0L
    }

    private fun part2(): Long {
        val string = readAllText("./src/main/kotlin/day24/input.txt")
        return 0
    }
}
