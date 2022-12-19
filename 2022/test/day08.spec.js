const {visibleFromTop, visibleFromLeft, visibleFromRight,  visibleFromBottom, isVisible, solve1, solve2} = require("$/src/day08/solve")
const assert = require("assert")

describe("visibleFromTop", () => {
    it("works as expected for case 1", () => {
        const input = [
            [3,0,3,7,3],
            [2,5,5,1,2],
            [6,5,3,3,2],
            [3,3,5,4,9],
            [3,5,3,9,0]
        ]

        assert.strictEqual(visibleFromTop(input, 1, 1), 1)
        assert.strictEqual(visibleFromTop(input, 2, 1), 1)
        assert.strictEqual(visibleFromTop(input, 3, 1), 1)
        assert.strictEqual(visibleFromTop(input, 1, 2), 1)
        assert.strictEqual(visibleFromTop(input, 2, 2), 1)
        assert.strictEqual(visibleFromTop(input, 3, 2), 2)
        assert.strictEqual(visibleFromTop(input, 1, 3), false)
        assert.strictEqual(visibleFromTop(input, 2, 3), false)
        assert.strictEqual(visibleFromTop(input, 3, 3), false)
    })
})

describe("visibleFromLeft", () => {
    it("works as expected for case 1", () => {
        const input = [
            [3,0,3,7,3],
            [2,5,5,1,2],
            [6,5,3,3,2],
            [3,3,5,4,9],
            [3,5,3,9,0]
        ]

        assert.strictEqual(visibleFromLeft(input, 1, 1), true)
        assert.strictEqual(visibleFromLeft(input, 2, 1), false)
        assert.strictEqual(visibleFromLeft(input, 3, 1), false)
        assert.strictEqual(visibleFromLeft(input, 1, 2), false)
        assert.strictEqual(visibleFromLeft(input, 2, 2), false)
        assert.strictEqual(visibleFromLeft(input, 3, 2), true)
        assert.strictEqual(visibleFromLeft(input, 1, 3), false)
        assert.strictEqual(visibleFromLeft(input, 2, 3), false)
        assert.strictEqual(visibleFromLeft(input, 3, 3), false)
    })
})

describe("isVisible", () => {
    it("works as expected", () => {
        const input = [
            [3,0,3,7,3],
            [2,5,5,1,2],
            [6,5,3,3,2],
            [3,3,5,4,9],
            [3,5,3,9,0]
        ]

        assert.strictEqual(isVisible(input, 1, 1), true)
        assert.strictEqual(isVisible(input, 1, 2), true)
        assert.strictEqual(isVisible(input, 1, 3), false)
        assert.strictEqual(isVisible(input, 2, 1), true)
        assert.strictEqual(isVisible(input, 2, 2), false)
        assert.strictEqual(isVisible(input, 2, 3), true)
        assert.strictEqual(isVisible(input, 3, 1), false)
        assert.strictEqual(isVisible(input, 3, 2), true)
        assert.strictEqual(isVisible(input, 3, 3), false)
    })
})

it("solve1 works as expected", () => {
    const input = [
        "30373",
        "25512",
        "65332",
        "33549",
        "35390",
        ""
    ]

    const result = solve2(input)

    assert.strictEqual(result, 21)
})
