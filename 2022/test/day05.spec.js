const {reverse, parseStacks, parseInstruction, solve1, solve2} = require("$/src/day05/day05")
const assert = require("assert")

describe("reverse", () => {
    it("works as expected for case 1", () =>
        assert.deepEqual([3, 2, 1], reverse([1, 2, 3]))
    )

    it("works as expected for case 2", () =>
        assert.deepEqual([4, 3, 2, 1], reverse([1, 2, 3, 4]))
    )
})
describe("parseStacks", () => {
    it("works as expected", () => {
        const input = [
            "    [D]    ",
            "[N] [C]    ",
            "[Z] [M] [P]",
            " 1   2   3 "
        ]

        const parsed = parseStacks(input)

        assert.strictEqual(3, parsed.length)
        assert.deepEqual(["Z", "N"], parsed[0])
        assert.deepEqual(["M", "C", "D"], parsed[1])
        assert.deepEqual(["P"], parsed[2])
    })
})

describe("parseInstruction", () => {
    it("works as expected", () => {
        const input = [
            "move 1 from 2 to 1",
            "move 3 from 1 to 3",
            "move 2 from 2 to 1",
            "move 1 from 1 to 2",
        ]

        assert.deepEqual([1, 1, 0], parseInstruction(input[0]))
        assert.deepEqual([3, 0, 2], parseInstruction(input[1]))
        assert.deepEqual([2, 1, 0], parseInstruction(input[2]))
        assert.deepEqual([1, 0, 1], parseInstruction(input[3]))
    })
})

it("solve1 works as expected", () => {
    const input = [
        "    [D]    ",
        "[N] [C]    ",
        "[Z] [M] [P]",
        " 1   2   3 ",
        "",
        "move 1 from 2 to 1",
        "move 3 from 1 to 3",
        "move 2 from 2 to 1",
        "move 1 from 1 to 2",
        ""
    ]

    const result = solve1(input)

    assert.strictEqual(result, "CMZ")
})

it("solve2 works as expected", () => {
    const input = [
        "    [D]    ",
        "[N] [C]    ",
        "[Z] [M] [P]",
        " 1   2   3 ",
        "",
        "move 1 from 2 to 1",
        "move 3 from 1 to 3",
        "move 2 from 2 to 1",
        "move 1 from 1 to 2",
        ""
    ]

    const result = solve2(input)

    assert.strictEqual(result, "MCD")
})
