const {solve1, solve2} = require("$/src/day14/solve")
const assert = require("assert")

it("solve1 works as expected", () => {
    const input = [
        "498,4 -> 498,6 -> 496,6",
        "503,4 -> 502,4 -> 502,9 -> 494,9",
        ""
    ]
    const res = solve1(input)
    assert.strictEqual(res, 24)
})

it("solve2 works as expected", () => {
    const input = [
        "498,4 -> 498,6 -> 496,6",
        "503,4 -> 502,4 -> 502,9 -> 494,9",
        ""
    ]
    const res = solve2(input)
    assert.strictEqual(res, 93)
})
