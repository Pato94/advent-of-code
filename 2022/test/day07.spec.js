const {parseList, parseDir, solve1, solve2} = require("$/src/day07/solve")
const assert = require("assert")

describe("parseList", () => {
    it("works as expected for case 1", () => {
        const input = [
            "dir e",
            "29116 f",
            "2557 g",
            "62596 h.lst"
        ]

        const parent = {}
        const result = parseList(input, parent, 0)

        assert.strictEqual(4, result)
        assert.strictEqual(true, parent.hasOwnProperty("e"))
        assert.strictEqual(true, parent.hasOwnProperty("f"))
        assert.strictEqual(true, parent.hasOwnProperty("g"))
        assert.strictEqual(true, parent.hasOwnProperty("h.lst"))
    })

    it("works as expected for case 2", () => {
        const input = [
            "dir e",
            "29116 f",
            "2557 g",
            "62596 h.lst",
            "$ cd e"
        ]

        const parent = {}
        const result = parseList(input, parent, 0)

        assert.strictEqual(4, result)
        assert.strictEqual(true, parent.hasOwnProperty("e"))
        assert.strictEqual(true, parent.hasOwnProperty("f"))
        assert.strictEqual(true, parent.hasOwnProperty("g"))
        assert.strictEqual(true, parent.hasOwnProperty("h.lst"))
    })

    it("works as expected for case 3", () => {
        const input = [
            "$ cd e"
        ]

        const parent = {}
        const result = parseList(input, parent, 0)

        assert.strictEqual(0, result)
    })
})

describe("parseDir", () => {
    it("works as expected for case 1", () => {
        const input = [
            "$ ls",
            "584 i",
            "$ cd .."
        ]

        const parent = {}
        const result = parseDir(input, parent, 0)

        assert.strictEqual(2, result)
    })

    it("works as expected for case 2", () => {
        const input = [
            "$ ls",
            "dir e",
            "29116 f",
            "2557 g",
            "62596 h.lst",
            "$ cd e",
            "$ ls",
            "584 i",
            "$ cd ..",
            "$ cd .."
        ]

        const parent = {}
        const result = parseDir(input, parent, 0)

        assert.strictEqual(9, result)
    })

    it("works as expected for case 3", () => {
        const input = [
            "$ cd /",
            "$ ls",
            "dir a",
            "14848514 b.txt",
            "8504156 c.dat",
            "dir d",
            "$ cd a",
            "$ ls",
            "dir e",
            "29116 f",
            "2557 g",
            "62596 h.lst",
            "$ cd e",
            "$ ls",
            "584 i",
            "$ cd ..",
            "$ cd ..",
            "$ cd d",
            "$ ls",
            "4060174 j",
            "8033020 d.log",
            "5626152 d.ext",
            "7214296 k"
        ]

        const parent = { "/": {} }
        const solution = parseDir(input, parent, 0)

        assert.strictEqual(24, solution)
    })
})

it("solve1 works as expected", () => {
    const input = [
        "$ cd /",
        "$ ls",
        "dir a",
        "14848514 b.txt",
        "8504156 c.dat",
        "dir d",
        "$ cd a",
        "$ ls",
        "dir e",
        "29116 f",
        "2557 g",
        "62596 h.lst",
        "$ cd e",
        "$ ls",
        "584 i",
        "$ cd ..",
        "$ cd ..",
        "$ cd d",
        "$ ls",
        "4060174 j",
        "8033020 d.log",
        "5626152 d.ext",
        "7214296 k",
        ""
    ]

    const solution = solve1(input)

    assert.strictEqual(95437, solution)
})

it("solve2 works as expected", () => {
    const input = [
        "$ cd /",
        "$ ls",
        "dir a",
        "14848514 b.txt",
        "8504156 c.dat",
        "dir d",
        "$ cd a",
        "$ ls",
        "dir e",
        "29116 f",
        "2557 g",
        "62596 h.lst",
        "$ cd e",
        "$ ls",
        "584 i",
        "$ cd ..",
        "$ cd ..",
        "$ cd d",
        "$ ls",
        "4060174 j",
        "8033020 d.log",
        "5626152 d.ext",
        "7214296 k",
        ""
    ]

    const solution = solve2(input)

    assert.strictEqual(24933642, solution)
})
