package day22

import readAllText
import java.util.*

class Day22 {

    fun main() {
        println("Solution for Day 22, part 1 is: ${part1()}")
        println("Solution for Day 22, part 2 is: ${part2()}")
    }

    private fun countOn(array: Array<Array<BooleanArray>>): Int {
        var count = 0
        for (grid in array) {
            for (row in grid) {
                for (i in row) {
                    if (i) count++
                }
            }
        }
        return count
    }

    private fun merge(a: IntRange, reactor: IntRange): IntRange {
        val start = if (a.first < reactor.first) {
            reactor.first
        } else {
            a.first
        }
        val end = if (a.last > reactor.last) {
            reactor.last
        } else {
            a.last
        }
        return start..end
    }

    data class Cube(val on: Boolean, val xRange: LongRange, val yRange: LongRange, val zRange: LongRange)

    private fun size(cube: Cube): Long {
        return size(cube.xRange) * size(cube.yRange) * size(cube.zRange)
    }

    private fun size(range: LongRange): Long {
        return range.last - range.first + 1
    }

    private fun intersect(old: Cube, new: Cube): Boolean {
        return intersect(old.xRange, new.xRange) &&
                intersect(old.yRange, new.yRange) &&
                intersect(old.zRange, new.zRange)
    }

    private fun intersect(a: LongRange, b: LongRange): Boolean {
        return b.first in a || b.last in a || a.first in b || a.last in b
    }

    private fun merge(old: Cube, new: Cube): List<Cube> {
        if (intersect(old, new)) {
            val list = mutableListOf<Cube>()
            val sortedX = setOf(
                        old.xRange.first,
                        old.xRange.last + 1,
                        new.xRange.first,
                        new.xRange.last + 1
                    ).sorted()
            var prevX = sortedX.first()
            for (x in sortedX.drop(1)) {
                val sortedY = setOf(
                        old.yRange.first,
                        old.yRange.last + 1,
                        new.yRange.first,
                        new.yRange.last + 1
                    ).sorted()
                var prevY = sortedY.first()
                for (y in sortedY.drop(1)) {
                    val sortedZ = setOf(
                            old.zRange.first,
                            old.zRange.last + 1,
                            new.zRange.first,
                            new.zRange.last + 1
                        ).sorted()
                    var prevZ = sortedZ.first()
                    for (z in sortedZ.drop(1)) {
                        val xRange = prevX until x
                        val yRange = prevY until y
                        val zRange = prevZ until z
                        if (intersect(Cube(false, xRange, yRange, zRange), new)) {
                            list.add(Cube(new.on, xRange, yRange, zRange))
                        } else if (intersect(Cube(false, xRange, yRange, zRange), old)) {
                            list.add(Cube(old.on, xRange, yRange, zRange))
                        }
                        prevZ = z
                    }
                    prevY = y
                }
                prevX = x
            }
            return list
        }
        return listOf(old, new)
    }

    private fun part1(): Int {
        val rangeX = -50..50
        val rangeY = -50..50
        val rangeZ = -50..50
        val reactor = Array(101) { Array(101) { BooleanArray(101) } }
        val string = readAllText("./src/main/kotlin/day22/sample.txt")
        // on x=10..12,y=10..12,z=10..12
        val regex = Regex("(on|off) x=(.*),y=(.*),z=(.*)")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val matched = regex.matchEntire(it)
            val (on, a, b, c) = matched!!.destructured

            val toRange: (String) -> IntRange = { i ->
                val values = i.split("..").map { it.toInt() }
                values[0]..values[1]
            }
            for (i in merge(toRange(a), rangeX)) {
                for (j in merge(toRange(b), rangeY)) {
                    for (k in merge(toRange(c), rangeZ)) {
                        reactor[i + 50][j + 50][k + 50] = on == "on"
                    }
                }
            }
        }
        return countOn(reactor)
    }

    private fun addCube(cubes: LinkedList<Cube>, cube: Cube) {
        val newCubes = mutableListOf<Cube>()
        val initialIndices = cubes.indices
        for (i in initialIndices) {
            val old = cubes.poll()
            if (intersect(old, cube)) {
                newCubes += merge(old, cube)
                break
            }
            cubes.add(old)
        }
        if (newCubes.isEmpty()) {
            if (cube.on) {
                cubes.add(cube)
            }
        } else {
            newCubes.forEach {
                addCube(cubes, it)
            }
        }
    }

    private fun part2(): Long {
        val cubes = LinkedList<Cube>()
        val string = readAllText("./src/main/kotlin/day22/input.txt")
        // on x=10..12,y=10..12,z=10..12
        val regex = Regex("(on|off) x=(.*),y=(.*),z=(.*)")
        string.lines().forEach {
            if (it.isBlank()) return@forEach

            val matched = regex.matchEntire(it)
            val (on, a, b, c) = matched!!.destructured

            val toRange: (String) -> LongRange = { i ->
                val values = i.split("..").map { it.toLong() }
                values[0]..values[1]
            }
            val newCube = Cube(on == "on", toRange(a), toRange(b), toRange(c))
            addCube(cubes, newCube)
        }
        var count = 0L
        for (i in cubes) {
            if (i.on) count += size(i)
        }
        return count
    }
}
