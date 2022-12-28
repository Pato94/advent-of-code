const {parseGraph, neighbors, canClimb, solve1, solve2} = require("$/src/day12/solve")
const assert = require("assert")

it("parseGraph works as expected", () => {
    const input = [
        "Sabqponm",
        "abcryxxl",
        "accszExk",
        "acctuvwj",
        "abdefghi",
        ""
    ]

    const [graph, start, target] = parseGraph(input)

    assert.deepStrictEqual(start, [0, 0])
    assert.deepStrictEqual(target, [2, 5])
})

it("neighbors works as expected", () => {
    const input = [
        "Sabqponm",
        "abcryxxl",
        "accszExk",
        "acctuvwj",
        "abdefghi",
        ""
    ]

    const [graph] = parseGraph(input)

    assert.deepStrictEqual(neighbors(graph, [0, 0]), [[1, 0], [0, 1]])
    assert.deepStrictEqual(neighbors(graph, [1, 1]), [[0, 1], [1, 0], [2, 1], [1, 2]])
})

it("canClimb works as expected", () => {
    const input = [
        "Sabqponm",
        "abcryxxl",
        "accszExk",
        "acctuvwj",
        "abdefghi",
        ""
    ]

    const [graph] = parseGraph(input)

    assert.strictEqual(canClimb(graph, [1, 2], [0, 2]), true)
    assert.strictEqual(canClimb(graph, [1, 2], [1, 1]), true)
    assert.strictEqual(canClimb(graph, [1, 2], [2, 2]), true)
    assert.strictEqual(canClimb(graph, [1, 2], [1, 3]), false)
    assert.strictEqual(canClimb(graph, [1, 3], [0, 3]), true)
    assert.strictEqual(canClimb(graph, [1, 3], [1, 2]), true)
    assert.strictEqual(canClimb(graph, [1, 3], [1, 4]), false)
    assert.strictEqual(canClimb(graph, [1, 3], [2, 3]), true)
})

it("solve1 works as expected", () => {
    const input = [
        "Sabqponm",
        "abcryxxl",
        "accszExk",
        "acctuvwj",
        "abdefghi",
        ""
    ]
    const res = solve1(input)

    assert.strictEqual(res, 31)
})
