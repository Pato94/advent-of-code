const table1 = {
    "B Z": 9, // PAPER VS SCISSORS, 6 + 3
    "A Y": 8, // ROCK VS PAPER, 6 + 2
    "C X": 7, // SCISSORS VS ROCK, 6 + 1
    "C Z": 6, // SCISSORS VS SCISSORS, 3 + 3
    "B Y": 5, // PAPER VS PAPER, 3 + 2
    "A X": 4, // ROCK VS ROCK, 3 + 1
    "A Z": 3, // ROCK VS SCISSORS, 0 + 3
    "C Y": 2, // SCISSORS VS PAPER, 0 + 2
    "B X": 1, // PAPER VS ROCK, 0 + 1
}

function solve1(input) {
    let score = 0
    for (const line of input) {
        if (line === "") continue
        score += table1[line]
    }
    return score
}

const table2 = {
    "B Z": 9, // PAPER VS SCISSORS, 6 + 3
    "A Z": 8, // ROCK VS PAPER, 6 + 2
    "C Z": 7, // SCISSORS VS ROCK, 6 + 1
    "C Y": 6, // SCISSORS VS SCISSORS, 3 + 3
    "B Y": 5, // PAPER VS PAPER, 3 + 2
    "A Y": 4, // ROCK VS ROCK, 3 + 1
    "A X": 3, // ROCK VS SCISSORS, 0 + 3
    "C X": 2, // SCISSORS VS PAPER, 0 + 2
    "B X": 1, // PAPER VS ROCK, 0 + 1
}

function solve2(input) {
    let score = 0
    for (const line of input) {
        if (line === "") continue
        score += table2[line]
    }
    return score
}

module.exports = { solve1, solve2 }
