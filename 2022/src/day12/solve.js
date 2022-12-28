function parseGraph(input) {
    let start = undefined
    const graph = []
    for (const line of input) {
        if (line === "") continue
        const items = line.split("");
        if (items.includes("S")) {
            start = [graph.length, items.indexOf("S")]
        }
        graph.push(items)
    }
    return [graph, start]
}

function neighbors(graph, current) {
    const [y, x] = current
    const ret = []
    if (y - 1 >= 0) {
        ret.push([y - 1, x])
    }
    if (x - 1 >= 0) {
        ret.push([y, x - 1])
    }
    if (y + 1 < graph.length) {
        ret.push([y + 1, x])
    }
    if (x + 1 < graph[0].length) {
        ret.push([y, x + 1])
    }
    return ret
}

function key(current) {
    return `y${current[0]}x${current[1]}`
}

function elevation(graph, current) {
    let char = graph[current[0]][current[1]]
    if (char === "S") char = "a"
    if (char === "E") char = "z"
    return char
}

function canClimb(graph, current, neighbor) {
    return (elevation(graph, current).charCodeAt(0) + 1) >= elevation(graph, neighbor).charCodeAt(0);
}

function findShortestPath(start, graph) {
    const visited = {}
    const next = [[start, 0]]
    while (next.length > 0) {
        const [current, steps] = next.shift()
        if (graph[current[0]][current[1]] === "E") {
            return steps
        }

        for (const neighbor of neighbors(graph, current)) {
            if (!visited[key(neighbor)] && canClimb(graph, current, neighbor)) {
                next.push([neighbor, steps + 1])
                visited[key(neighbor)] = true
            }
        }
    }
    return Number.MAX_VALUE
}

function solve1(input) {
    const [graph, start] = parseGraph(input)
    return findShortestPath(start, graph)
}

function solve2(input) {
    const [graph] = parseGraph(input)
    let min = Number.MAX_VALUE
    for (let y = 0; y < graph.length; y++) {
        for (let x = 0; x < graph[y].length; x++) {
            if (elevation(graph, [y, x]) === "a") {
                const length = findShortestPath([y, x], graph, {});
                if (length < min) min = length
            }
        }
    }
    return min
}

module.exports = {parseGraph, neighbors, canClimb, solve1, solve2}
