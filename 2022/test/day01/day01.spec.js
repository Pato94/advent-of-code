const { solve1, solve2 } = require("$/src/day01/day01")
const assert = require("assert")

it("solve1 works as expected", () => {
    const input = [
        "1000",
        "2000",
        "3000",
        "",
        "4000",
        "",
        "5000",
        "6000",
        "",
        "7000",
        "8000",
        "9000",
        "",
        "10000",
        ""
    ]
    const solution = solve1(input)

    assert.strictEqual(24000, solution)
})

it("solve2 works as expected", () => {
    const input = [
        "1000",
        "2000",
        "3000",
        "",
        "4000",
        "",
        "5000",
        "6000",
        "",
        "7000",
        "8000",
        "9000",
        "",
        "10000",
        ""
    ]
    const sum = solve2(input)

    assert.strictEqual(45000, sum)
})
