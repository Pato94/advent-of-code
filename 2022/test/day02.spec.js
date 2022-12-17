const { solve1, solve2 } = require("$/src/day02/day02")
const assert = require("assert")

it("solve1 should work as expected", () => {
    const input = [
        "A Y",
        "B X",
        "C Z",
        ""
    ]

    const solution = solve1(input)

    assert.strictEqual(15, solution)
})

it("solve2 should work as expected", () => {
    const input = [
        "A Y",
        "B X",
        "C Z",
        ""
    ]

    const solution = solve2(input)

    assert.strictEqual(12, solution)
})
