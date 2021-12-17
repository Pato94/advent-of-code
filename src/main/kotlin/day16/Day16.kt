package day16

import readAllText
import java.lang.IllegalStateException
import java.lang.StringBuilder

class Day16 {

    fun main() {
        println("Solution for Day 16, part 1 is: ${part1()}")
        println("Solution for Day 16, part 2 is: ${part2()}")
    }

    interface Packet {
        val bytes: Int
        fun versionSum(): Int
        fun calculate(): Long
    }

    data class LiteralPacket(val version: Int, val type: Int, val value: Long, override val bytes: Int) : Packet {
        override fun versionSum(): Int {
            return version
        }

        override fun calculate(): Long {
            return value
        }
    }

    data class OperatorPacket(
        val version: Int,
        val type: Int,
        val length: Int,
        val packets: List<Packet>,
        override val bytes: Int
    ) : Packet {
        override fun versionSum(): Int {
            return version + packets.sumOf { it.versionSum() }
        }

        override fun calculate(): Long {
            return when (type) {
                0 -> packets.sumOf { it.calculate() }
                1 -> packets.foldRight(1) { a, e -> a.calculate() * e }
                2 -> packets.minOf { it.calculate() }
                3 -> packets.maxOf { it.calculate() }
                5 -> if (packets[0].calculate() > packets[1].calculate()) 1 else 0
                6 -> if (packets[0].calculate() < packets[1].calculate()) 1 else 0
                7 -> if (packets[0].calculate() == packets[1].calculate()) 1 else 0
                else -> throw IllegalStateException("Unexpected type $type")
            }
        }
    }

    private fun parsePacket(string: String): Packet {
        val version = string.substring(0, 3).toInt(2)
        val type = string.substring(3, 6).toInt(2)
        if (type == 4) {
            var stop = false
            var i = 6
            val num = StringBuilder("")
            while (!stop) {
                val numSource = string.substring(i, i + 5)
                num.append(numSource.substring(1, 5))
                stop = numSource[0] == '0'
                i += 5
            }
            return LiteralPacket(version, type, num.toString().toLong(2), i)
        } else {
            val packets = mutableListOf<Packet>()
            val length = string.substring(6, 7)[0] - '0'
            var read = 0
            when (length) {
                0 -> {
                    val totalLengthInBits = string.substring(7, 22).toInt(2)
                    while (read < totalLengthInBits) {
                        val newPacket = parsePacket(string.substring(22 + read))
                        packets.add(newPacket)
                        read += newPacket.bytes
                    }
                    read += 15
                }
                1 -> {
                    val totalPackets = string.substring(7, 18).toInt(2)
                    var i = 0
                    while (i < totalPackets) {
                        val newPacket = parsePacket(string.substring(18 + read))
                        packets.add(newPacket)
                        read += newPacket.bytes
                        i++
                    }
                    read += 11
                }
                else -> throw IllegalStateException("Unexpected length $length")
            }

            return OperatorPacket(version, type, length, packets, read + 7)
        }
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day16/input.txt")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            return parsePacket(toBinary(it)).versionSum()
        }
        return 0
    }

    private fun part2(): Long {
        val string = readAllText("./src/main/kotlin/day16/input.txt")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            return parsePacket(toBinary(it)).calculate()
        }
        return 0
    }

    private fun toBinary(input: String): String {
        val result = StringBuilder("")
        for (c in input) {
            result.append(
                when (c) {
                    '0' -> "0000"
                    '1' -> "0001"
                    '2' -> "0010"
                    '3' -> "0011"
                    '4' -> "0100"
                    '5' -> "0101"
                    '6' -> "0110"
                    '7' -> "0111"
                    '8' -> "1000"
                    '9' -> "1001"
                    'A' -> "1010"
                    'B' -> "1011"
                    'C' -> "1100"
                    'D' -> "1101"
                    'E' -> "1110"
                    'F' -> "1111"
                    else -> throw IllegalStateException("Unexpected char $c")
                }
            )
        }
        return result.toString()
    }

}
