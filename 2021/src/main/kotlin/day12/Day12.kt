package day12

import readAllText

class Day12 {

    fun main() {
        println("Solution for Day 12, part 1 is: ${part1()}")
        println("Solution for Day 12, part 2 is: ${part2()}")
    }

    private fun part1(): Int {
        val string = readAllText("./src/main/kotlin/day12/input.txt")
        val map = mutableMapOf<String, MutableList<String>>()
        val regex = Regex("(\\w+)-(\\w+)")
        string.lines().forEach { line ->
            if (line.isBlank()) return@forEach
            val (a, b) = regex.matchEntire(line)!!.destructured
            map[a] = (map[a] ?: mutableListOf()).also { it.add(b) }
            map[b] = (map[b] ?: mutableListOf()).also { it.add(a) }
        }
        return pathsFrom("start", map, emptyList()).size
    }

    private fun pathsFrom(key: String, map: Map<String, List<String>>, currentPath: List<String>): List<List<String>> {
        if (key in currentPath && key[0].isLowerCase()) return emptyList() // Don't repeat small caves
        val nextPath = currentPath.toMutableList().also { it.add(key) }
        if (key == "end") return listOf(nextPath)
        val paths = mutableListOf<List<String>>()
        val current = map[key]!!
        current.forEach { next ->
            paths.addAll(
                pathsFrom(next, map, nextPath).filter { it.isNotEmpty() }
            )
        }
        return paths
    }

    private fun pathsFrom2(key: String, map: Map<String, List<String>>, prevPath: List<String>, visitedSmallTwice: Boolean): Int {
        var newVisitedSmallTwice = visitedSmallTwice
        if (key in prevPath && key[0].isLowerCase()) {
            if (key == "start" || visitedSmallTwice) {
                return 0
            } else {
                newVisitedSmallTwice = true
            }
        }
        if (key == "end") return 1
        var paths = 0
        val current = map[key]!!
        val nextPath = prevPath.toMutableList().also { it.add(key) }
        current.forEach { next ->
           paths += pathsFrom2(next, map, nextPath, newVisitedSmallTwice)
        }
        return paths
    }

    private fun part2(): Int {
        val string = readAllText("./src/main/kotlin/day12/input.txt")
        val map = mutableMapOf<String, MutableList<String>>()
        val regex = Regex("(\\w+)-(\\w+)")
        string.lines().forEach { line ->
            if (line.isBlank()) return@forEach
            val (a, b) = regex.matchEntire(line)!!.destructured
            map[a] = (map[a] ?: mutableListOf()).also { it.add(b) }
            map[b] = (map[b] ?: mutableListOf()).also { it.add(a) }
        }
        return pathsFrom2("start", map, emptyList(), false)
    }
}
