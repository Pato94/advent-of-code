const { shouldUpdate, update, solve1, solve2 } = require("$/src/day09/solve")
const assert = require("assert")

describe("shouldUpdate", () => {
    it("works as expected for case 1", () => {
        assert.strictEqual(shouldUpdate({ x: 2, y: 1 }, { x: 1, y: 1 }), false)
    })

    it("works as expected for case 2", () => {
        assert.strictEqual(shouldUpdate({ x: 1, y: 1 }, { x: 2, y: 2 }), false)
    })

    it("works as expected for case 3", () => {
        assert.strictEqual(shouldUpdate({ x: 1, y: 1 }, { x: 1, y: 1 }), false)
    })

    it("works as expected for case 4", () => {
        assert.strictEqual(shouldUpdate({ x: 3, y: 1 }, { x: 1, y: 1 }), true)
    })

    it("works as expected for case 4", () => {
        assert.strictEqual(shouldUpdate({ x: 1, y: 3 }, { x: 1, y: 1 }), true)
    })
})

describe("update", () => {
    it("works as expected for case 1", () => {
        const head = { x: 3, y: 1 }
        const tail = { x: 1, y: 1 }

        update(head, tail)

        assert.strictEqual(tail.x, 2)
        assert.strictEqual(tail.y, 1)
    })

    it("works as expected for case 2", () => {
        const head = { x: 1, y: 3 }
        const tail = { x: 1, y: 1 }

        update(head, tail)

        assert.strictEqual(tail.x, 1)
        assert.strictEqual(tail.y, 2)
    })

    it("works as expected for case 3", () => {
        const head = { x: 2, y: 1 }
        const tail = { x: 1, y: 3 }

        update(head, tail)

        assert.strictEqual(tail.x, 2)
        assert.strictEqual(tail.y, 2)
    })
})

it("solve1 works as expected", () => {
    const res = solve1([
        "R 4",
        "U 4",
        "L 3",
        "D 1",
        "R 4",
        "D 1",
        "L 5",
        "R 2",
        ""
    ])

    assert.strictEqual(13, res)
})

describe("solve2", () => {
    it("works as expected for case 1", () => {
        const res = solve2([
            "R 4",
            "U 4",
            "L 3",
            "D 1",
            "R 4",
            "D 1",
            "L 5",
            "R 2",
            ""
        ])

        assert.strictEqual(1, res)
    })

    it("works as expected for case 2", () => {
        const res = solve2([
            "R 5",
            "U 8",
            "L 8",
            "D 3",
            "R 17",
            "D 10",
            "L 25",
            "U 20",
            ""
        ])

        assert.strictEqual(36, res)
    })
})
