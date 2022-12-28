function parseMonkey(input) {
    const items = []
    let operation = undefined
    const test = {}
    let found = 0
    for (const line of input) {
        const trimmed = line.trim();
        if (trimmed === "") continue
        if (trimmed.startsWith("Starting items: ")) {
            found++
            items.push(...trimmed.substring("Starting items: ".length).split(", ").map(it => parseInt(it)))
        }
        if (trimmed.startsWith("Operation: new = ")) {
            found++
            operation = trimmed.substring("Operation: new = ".length)
        }
        if (trimmed.startsWith("Test: divisible by ")) {
            found++
            test.divisibleBy = parseInt(trimmed.substring("Test: divisible by ".length))
        }
        if (trimmed.startsWith("If true: throw to monkey ")) {
            found++
            test.targetTrue = parseInt(trimmed.substring("If true: throw to monkey ".length))
        }
        if (trimmed.startsWith("If false: throw to monkey ")) {
            found++
            test.targetFalse = parseInt(trimmed.substring("If false: throw to monkey ".length))
        }
    }
    if (found < 5) throw Error("Missing property") // sanity check
    return { items, operation, test, inspected: 0 }
}

function parseMonkeys(input) {
    const monkeys = []
    let monkeyInput = []
    for (const line of input) {
        if (line === "") {
            monkeys.push(parseMonkey(monkeyInput))
            monkeyInput = []
            continue
        }
        monkeyInput.push(line)
    }
    if (monkeyInput.length > 0) {
        monkeys.push(parseMonkey(monkeyInput))
    }
    return monkeys
}

function solve1(input) {
    const monkeys = parseMonkeys(input)
    for (let i = 0; i < 20; i++) {
        for (const monkey of monkeys) {
            while (monkey.items.length > 0) {
                monkey.inspected++
                let old = monkey.items.shift()
                let next = eval(monkey.operation)
                next = Math.floor(next / 3)
                if (next % monkey.test.divisibleBy === 0) {
                    monkeys[monkey.test.targetTrue].items.push(next)
                } else {
                    monkeys[monkey.test.targetFalse].items.push(next)
                }
            }
        }
    }
    monkeys.sort((m1, m2) => m2.inspected - m1.inspected)
    return monkeys[0].inspected * monkeys[1].inspected
}

function solve2(input) {
    const monkeys = parseMonkeys(input)
    // Couldn't figure it out
    // stolen from https://github.com/zebalu/advent-of-code-2022/blob/master/aoc2022/src/main/java/io/github/zebalu/aoc2022/Day11.java
    const multiplier = monkeys.map(it => it.test.divisibleBy).reduce((a, e) => a * e, 1)
    for (let i = 0; i < 10000; i++) {
        for (const monkey of monkeys) {
            while (monkey.items.length > 0) {
                monkey.inspected++
                let old = monkey.items.shift()
                let next = eval(monkey.operation) % multiplier
                if (next % monkey.test.divisibleBy === 0) {
                    monkeys[monkey.test.targetTrue].items.push(next)
                } else {
                    monkeys[monkey.test.targetFalse].items.push(next)
                }
            }
        }
    }
    monkeys.sort((m1, m2) => m2.inspected - m1.inspected)
    return monkeys[0].inspected * monkeys[1].inspected
}

module.exports = {parseMonkey, parseMonkeys, solve1, solve2}
