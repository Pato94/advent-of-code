const {findDuplicated, priority, solve1, findBadge, solve2} = require("$/src/day03/day03")
const assert = require("assert")

describe("findDuplicated", () => {
    it("works for case 1", () => {
        const dup = findDuplicated("vJrwpWtwJgWrhcsFMMfFFhFp")

        assert.strictEqual(dup, "p")
    })
    it("works for case 2", () => {
        const dup = findDuplicated("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL")

        assert.strictEqual(dup, "L")
    })
    it("works for case 3", () => {
        const dup = findDuplicated("PmmdzqPrVvPwwTWBwg")

        assert.strictEqual(dup, "P")
    })
    it("works for case 4", () => {
        const dup = findDuplicated("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn")

        assert.strictEqual(dup, "v")
    })
    it("works for case 5", () => {
        const dup = findDuplicated("ttgJtRGJQctTZtZT")

        assert.strictEqual(dup, "t")
    })
    it("works for case 6", () => {
        const dup = findDuplicated("CrZsJsPPZsGzwwsLwLmpwMDw")

        assert.strictEqual(dup, "s")
    })
})

describe("priority", () => {
    it("works for lowercase", () => {
        assert.strictEqual(1, priority("a"))
        assert.strictEqual(3, priority("c"))
        assert.strictEqual(26, priority("z"))
    })

    it("works for uppercase", () => {
        assert.strictEqual(27, priority("A"))
        assert.strictEqual(29, priority("C"))
        assert.strictEqual(52, priority("Z"))
    })
})

it("solve1 works as expected", () => {
    const input = [
        "vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw",
        ""
    ]

    const solution = solve1(input)

    assert.strictEqual(157, solution)
})


describe("findBadge", () => {
    it("works for case 1", () => {
        const badge = findBadge(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg"
        )

        assert.strictEqual("r", badge)
    })

    it("works for case 2", () => {
        const badge = findBadge(
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw"
        )

        assert.strictEqual("Z", badge)
    })
})

it("solve2 works as expected", () => {
    const input = [
        "vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw",
        ""
    ]

    const solution = solve2(input)

    assert.strictEqual(70, solution)
})
