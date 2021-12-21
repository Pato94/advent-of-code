import day20.Day20
import day21.Day21
import kotlin.system.measureTimeMillis

fun main() {
//    logTime { Day01().main() }
//    logTime { Day02().main() }
//    logTime { Day03().main() }
//    logTime { Day04().main() }
//    logTime { Day05().main() }
//    logTime { Day06().main() }
//    logTime { Day07().main() }
//    logTime { Day08().main() }
//    logTime { Day09().main() }
//    logTime { Day10().main() }
//    logTime { Day11().main() }
//    logTime { Day12().main() }
//    logTime { Day13().main() }
//    logTime { Day14().main() }
//    logTime { Day15().main() }
//    logTime { Day16().main() }
//    logTime { Day17().main() }
//    logTime { Day18().main() }
//    logTime { Day19().main() }
//    logTime { Day20().main() }
    logTime { Day21().main() }
}

fun logTime(f: () -> Unit) {
    val time = measureTimeMillis(f)
    println("Took $time millis")
}
