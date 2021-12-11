import day01.Day01
import day02.Day02
import day03.Day03
import day04.Day04
import day05.Day05
import day06.Day06
import day07.Day07
import day08.Day08
import day09.Day09
import day10.Day10
import day11.Day11
import kotlin.system.measureTimeMillis

fun main() {
    logTime { Day01().main() }
    logTime { Day02().main() }
    logTime { Day03().main() }
    logTime { Day04().main() }
    logTime { Day05().main() }
    logTime { Day06().main() }
    logTime { Day07().main() }
    logTime { Day08().main() }
    logTime { Day09().main() }
    logTime { Day10().main() }
    logTime { Day11().main() }
}

fun logTime(f: () -> Unit) {
    val time = measureTimeMillis(f)
    println("Took $time millis")
}
