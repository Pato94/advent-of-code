const {solve1, solve2} = require("$/src/day06/solve")
const assert = require("assert")

it("solve1 works as expected", () => {
    const input = [
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
        "bvwbjplbgvbhsrlpgdmjqwftvncz",
        "nppdvjthqldpwncqszvftbrmjlhg",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
    ]

    assert.strictEqual(7, solve1([input[0]]))
    assert.strictEqual(5, solve1([input[1]]))
    assert.strictEqual(6, solve1([input[2]]))
    assert.strictEqual(10, solve1([input[3]]))
    assert.strictEqual(11, solve1([input[4]]))
})

it("solve2 works as expected", () => {
    const input = [
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
        "bvwbjplbgvbhsrlpgdmjqwftvncz",
        "nppdvjthqldpwncqszvftbrmjlhg",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
    ]

    assert.strictEqual(19, solve2([input[0]]))
    assert.strictEqual(23, solve2([input[1]]))
    assert.strictEqual(23, solve2([input[2]]))
    assert.strictEqual(29, solve2([input[3]]))
    assert.strictEqual(26, solve2([input[4]]))
})
