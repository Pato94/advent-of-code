const {range, size, solve1, solve2} = require("$/src/day04/day04")
const assert = require("assert")

describe("range", () => {
    it("works for case 1", () => {
        const result = range("1-9")
        assert.deepStrictEqual([1, 9], result)
    })
    it("works for case 2", () => {
        const result = range("1-10")
        assert.deepStrictEqual([1, 10], result)
    })
})

describe("size", () => {
    it("works for case 1", () => {
        const result = size([1, 9])
        assert.strictEqual(8, result)
    })

    it("works for case 2", () => {
        const result = size([1, 1])
        assert.strictEqual(0, result)
    })
})

it("solve1 works as expected", () => {
    const input = [
        "2-4,6-8",
        "2-3,4-5",
        "5-7,7-9",
        "2-8,3-7",
        "6-6,4-6",
        "2-6,4-8",
        ""
    ]

    const result = solve1(input)

    assert.strictEqual(2, result)
})

it("solve2 works as expected", () => {
    const input = [
        "2-4,6-8",
        "2-3,4-5",
        "5-7,7-9",
        "2-8,3-7",
        "6-6,4-6",
        "2-6,4-8",
        ""
    ]

    const result = solve2(input)

    assert.strictEqual(4, result)
})
