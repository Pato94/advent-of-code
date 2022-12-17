function reverse(array) {
    const limit = Math.floor(array.length / 2);
    for (let i = 0; i < limit; i++) {
        const dest = array.length - (i + 1);
        const aux = array[dest]
        array[dest] = array[i]
        array[i] = aux
    }
    return array
}

function parseStacks(input) {
    let maxIdx = 0
    const stacks = {}
    for (const line of input) {
        for (let i = 0; i < line.length; i++) {
            if (line[i] >= "A" && line[i] <= "Z") {
                const idx = Math.floor(i / 4)
                if (idx > maxIdx) {
                    maxIdx = idx
                }
                if (!stacks[idx]) stacks[idx] = []
                stacks[idx].push(line[i])
            }
        }
    }
    return Array(maxIdx + 1).fill(0)
        .map((_, idx) => reverse(stacks[idx]) || [])
}

function parseInstruction(line) {
    const [_, q, src, dst] = /move (\d+) from (\d+) to (\d+)/.exec(line).map(it => parseInt(it))
    return [q, src - 1, dst - 1]
}

function solve1(input) {
    let stacks = undefined
    const stacksSource = []
    for (const line of input) {
        if (line === "") {
            if (!stacks) {
                stacks = parseStacks(stacksSource)
            }
            continue
        }
        if (!stacks) {
            stacksSource.push(line)
            continue
        }
        const [q, src, dst] = parseInstruction(line)
        for (let i = 0; i < q; i++) {
            stacks[dst].push(stacks[src].pop())
        }
    }
    let result = ""
    for (const stack of stacks) {
        result = result + stack.pop()
    }
    return result
}

function solve2(input) {
    let stacks = undefined
    const stacksSource = []
    for (const line of input) {
        if (line === "") {
            if (!stacks) {
                stacks = parseStacks(stacksSource)
            }
            continue
        }
        if (!stacks) {
            stacksSource.push(line)
            continue
        }
        const [q, src, dst] = parseInstruction(line)
        const aux = []
        for (let i = 0; i < q; i++) {
            aux.push(stacks[src].pop())
        }
        for (let i = 0; i < q; i++) {
            stacks[dst].push(aux.pop())
        }
    }
    let result = ""
    for (const stack of stacks) {
        result = result + stack.pop()
    }
    return result
}

module.exports = {reverse, parseStacks, parseInstruction, solve1, solve2}
