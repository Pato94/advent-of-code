function parseList(input, parent, start) {
    let curr = start
    while (curr < input.length) {
        const instruction = input[curr]
        if (instruction === "") {
            curr++; continue
        }
        if (instruction.startsWith("$")) {
            break
        } else if (instruction.startsWith("dir")) {
            parent[instruction.substring(4)] = {}
        } else {
            const [_, size, name] = /(\d+) (.*)/.exec(instruction)
            parent[name] = {
                size: parseInt(size)
            }
        }
        curr++
    }
    return curr - start
}

function parseDir(input, parent, start) {
    let curr = start
    while (curr < input.length) {
        const instruction = input[curr]
        if (instruction === "$ ls") {
            const consumed = parseList(input, parent, curr + 1)
            curr += consumed + 1
            continue
        }
        const [_, dest] = /\$ cd (.*)/.exec(instruction)
        if (dest === "..") {
            curr++
            break
        }
        curr += parseDir(input, parent[dest], curr + 1)
    }
    return curr - start + 1
}

function sizeOf(node) {
    if (node.size) {
        // means it is a file
        return node.size
    }
    // means it is a dir
    let sum = 0
    for (const nodeKey in node) {
        sum += sizeOf(node[nodeKey])
    }
    return sum
}

function collect1(node, list) {
    for (const nodeKey in node) {
        // We're only interested in files
        if (!node[nodeKey].size) {
            if (sizeOf(node[nodeKey]) < 100_000) {
                list.push(node[nodeKey])
            }
            collect1(node[nodeKey], list)
        }
    }
}

function solve1(input) {
    const parent = { "/": {} }
    parseDir(input, parent, 0)

    const list = []
    collect1(parent, list)
    return list.reduce((a, e) => a + sizeOf(e), 0)
}

function collect2(node, list, target) {
    for (const nodeKey in node) {
        // We're only interested in files
        if (!node[nodeKey].size) {
            if (sizeOf(node[nodeKey]) >= target) {
                list.push(sizeOf(node[nodeKey]))
            }
            collect2(node[nodeKey], list, target)
        }
    }
}

function solve2(input) {
    const parent = { "/": {} }
    parseDir(input, parent, 0)

    const available = 70000000 - sizeOf(parent["/"])
    const required = 30000000
    const target = required - available

    const list = []
    collect2(parent, list, target)

    return Math.min(...list)
}

module.exports = {parseList, parseDir, solve1, solve2}
