const table = {
    "B Z": 9,
    "A Y": 8,
    "C X": 7,
    "C Z": 6,
    "B Y": 5,
    "A X": 4,
    "A Z": 3,
    "C Y": 2,
    "B X": 1,
}

function solve1(input) {
    let score = 0
    for (const line of input) {
        if (line === "") continue
        score += table[line]
    }
    return score
}

module.exports = { solve1 }
