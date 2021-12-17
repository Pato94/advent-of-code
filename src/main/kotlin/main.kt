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
import day12.Day12
import day13.Day13
import day14.Day14
import day15.Day15
import day16.Day16
import day17.Day17
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
    logTime { Day12().main() }
    logTime { Day13().main() }
    logTime { Day14().main() }
    logTime { Day15().main() }
    logTime { Day16().main() }
    logTime { Day17().main() }
}

fun logTime(f: () -> Unit) {
    val time = measureTimeMillis(f)
    println("Took $time millis")
}
