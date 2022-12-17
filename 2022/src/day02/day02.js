

function wins(n, e) {
    if (e === 3) { // scissors
        return n === 1 // rock
    }
    return n > e
}

function solve1(input) {
    let score = 0
    for (const line of input) {
        if (line === "") continue
        console.log(`line is ${line}`)
        let [e, n] = line.split(" ").map((it) => it.charCodeAt(0))
        e = e - "A".charCodeAt(0) + 1
        n = n - "X".charCodeAt(0) + 1
        if (wins(n, e)) {
            console.log(`we win`)
            score += 6
        } else if (n === e) {
            console.log(`we draw`)
            score += 3
        } else {
            console.log(`we lose`)
        }
        score += n
        console.log(`new score ${score}`)
    }
    return score
}

module.exports = { solve1 }
