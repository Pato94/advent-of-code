function printCaveRegion(graph, xRange, yRange) {
    for (let i = yRange[0]; i <= yRange[1]; i++) {
        const line = []
        for (let j = xRange[0]; j <= xRange[1]; j++) {
            if (graph[i][j] === 1) {
                line.push("#")
            } else if(graph[i][j] === 2) {
                line.push("o")
            } else {
                line.push(".")
            }
        }
        console.log(line.join(""))
    }
}

function hugeEmptyArray() {
    return Array(1024).fill(0).map(() => Array(1024).fill(0));
}

function nextPos(sandPos, graph) {
    // attempt position below
    if (graph[sandPos[1] + 1][sandPos[0]] === 0) {
        return [sandPos[0], sandPos[1] + 1]
    }
    // attempt diagonal left
    if (graph[sandPos[1] + 1][sandPos[0] - 1] === 0) {
        return [sandPos[0] - 1, sandPos[1] + 1]
    }
    // attempt diagonal right
    if (graph[sandPos[1] + 1][sandPos[0] + 1] === 0) {
        return [sandPos[0] + 1, sandPos[1] + 1]
    }
    // no options left, stuck
    return undefined
}

function solve1(input) {
    const graph = hugeEmptyArray()
    let maxY = Number.MIN_VALUE
    for (const line of input) {
        if (line === "") continue
        const points = line.split(" -> ").map(it => it.split(","))
        if (points.length === 0) continue
        let prev = points[0]
        for (let i = 1; i < points.length; i++) {
            const current = points[i]
            if (prev[0] === current[0]) {
                // draw vertical line
                const start = Math.min(prev[1], current[1]);
                const end = Math.max(prev[1], current[1]);
                for (let j = start; j <= end; j++) {
                    graph[j][prev[0]] = 1
                }
                if (end > maxY) {
                    maxY = end
                }
            } else {
                // draw horizontal line
                const start = Math.min(prev[0], current[0])
                const end = Math.max(prev[0], current[0])
                for (let j = start; j <= end; j++) {
                    graph[prev[1]][j] = 1
                }
                if (prev[1] > maxY) {
                    maxY = prev[1]
                }
            }
            prev = current
        }
    }
    let sands = 0
    let sandPos = [500, 0]
    while (sandPos[1] <= maxY) {
        const next = nextPos(sandPos, graph);
        if (next) {
            sandPos = next
        } else {
            graph[sandPos[1]][sandPos[0]] = 2
            sandPos = [500, 0]
            sands++
        }
    }
    return sands
}

function solve2(input) {
    const graph = hugeEmptyArray()
    let maxY = Number.MIN_VALUE
    for (const line of input) {
        if (line === "") continue
        const points = line.split(" -> ").map(it => it.split(","))
        if (points.length === 0) continue
        let prev = points[0]
        for (let i = 1; i < points.length; i++) {
            const current = points[i]
            if (prev[0] === current[0]) {
                // draw vertical line
                const start = Math.min(prev[1], current[1]);
                const end = Math.max(prev[1], current[1]);
                for (let j = start; j <= end; j++) {
                    graph[j][prev[0]] = 1
                }
                if (end > maxY) {
                    maxY = end
                }
            } else {
                // draw horizontal line
                const start = Math.min(prev[0], current[0])
                const end = Math.max(prev[0], current[0])
                for (let j = start; j <= end; j++) {
                    graph[prev[1]][j] = 1
                }
                if (prev[1] > maxY) {
                    maxY = prev[1]
                }
            }
            prev = current
        }
    }
    // set's the floor
    for (let i = 0; i < graph[2 + maxY].length; i++) {
        graph[2 + maxY][i] = 1
    }
    let sands = 0
    let sandPos = [500, 0]
    while (true) {
        const next = nextPos(sandPos, graph);
        if (next) {
            sandPos = next
        } else {
            if (sandPos[1] === 0) break
            graph[sandPos[1]][sandPos[0]] = 2
            sandPos = [500, 0]
            sands++
        }
    }
    return sands + 1
}

module.exports = {solve1, solve2}
