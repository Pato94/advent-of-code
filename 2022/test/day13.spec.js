const {compare, solve1, solve2} = require("$/src/day13/solve")
const assert = require("assert")

describe('compare', () => {
    it('works as expected for case 1', () => {
        const a = [1, 1, 3, 1, 1]
        const b = [1, 1, 5, 1, 1]

        assert.strictEqual(compare(a, b), 1)
    })

    it("works as expected for case 2", () => {
        const a = [[1], [2, 3, 4]]
        const b = [[1], 4]

        assert.strictEqual(compare(a, b), 1)
    })

    it("works as expected for case 3", () => {
        const a = [9]
        const b = [[8, 7, 6]]

        assert.strictEqual(compare(a, b), -1)
    })

    it("works as expected for case 4", () => {
        const a = [[4, 4], 4, 4]
        const b = [[4, 4], 4, 4, 4]

        assert.strictEqual(compare(a, b), 1)
    })

    it("works as expected for case 5", () => {
        const a = [7, 7, 7, 7]
        const b = [7, 7, 7]

        assert.strictEqual(compare(a, b), -1)
    })

    it("works as expected for case 6", () => {
        const a = []
        const b = [3]

        assert.strictEqual(compare(a, b), 1)
    })

    it("works as expected for case 7", () => {
        const a = [[[]]]
        const b = [[]]

        assert.strictEqual(compare(a, b), -1)
    })

    it("works as expected for case 8", () => {
        const a = [1, [2, [3, [4, [5, 6, 7]]]], 8, 9]
        const b = [1, [2, [3, [4, [5, 6, 0]]]], 8, 9]

        assert.strictEqual(compare(a, b), -1)
    })
});


it("solve1 works as expected", () => {
    const input = ["[1,1,3,1,1]",
        "[1,1,5,1,1]",
        "",
        "[[1],[2,3,4]]",
        "[[1],4]",
        "",
        "[9]",
        "[[8,7,6]]",
        "",
        "[[4,4],4,4]",
        "[[4,4],4,4,4]",
        "",
        "[7,7,7,7]",
        "[7,7,7]",
        "",
        "[]",
        "[3]",
        "",
        "[[[]]]",
        "[[]]",
        "",
        "[1,[2,[3,[4,[5,6,7]]]],8,9]",
        "[1,[2,[3,[4,[5,6,0]]]],8,9]",
        ""
    ]

    const res = solve1(input)

    assert.strictEqual(res, 13)
})

it("solve2 works as expected", () => {
    const input = ["[1,1,3,1,1]",
        "[1,1,5,1,1]",
        "",
        "[[1],[2,3,4]]",
        "[[1],4]",
        "",
        "[9]",
        "[[8,7,6]]",
        "",
        "[[4,4],4,4]",
        "[[4,4],4,4,4]",
        "",
        "[7,7,7,7]",
        "[7,7,7]",
        "",
        "[]",
        "[3]",
        "",
        "[[[]]]",
        "[[]]",
        "",
        "[1,[2,[3,[4,[5,6,7]]]],8,9]",
        "[1,[2,[3,[4,[5,6,0]]]],8,9]",
        ""
    ]

    const res = solve2(input)

    assert.strictEqual(res, 140)
})
