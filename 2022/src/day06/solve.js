function update(last, char) {
    for (let i = 0; i < last.length - 1; i++) {
        last[i] = last[i + 1]
    }
    last[last.length - 1] = char
}

function hasDuplicates(array) {
    for (let i = 0; i < array.length - 1; i++) {
        for (let j = i + 1; j < array.length; j++) {
            if (array[i] === array[j]) return true
        }
    }
    return false
}

function solve(input, limit) {
    for (const line of input) {
        let last = []
        for (let i = 0; i < line.length; i++) {
            if (last.length < limit) {
                last.push(line[i])
                continue
            }
            if (hasDuplicates(last)) {
                update(last, line[i])
            } else {
                return i
            }
        }
    }
}

function solve1(input) {
    return solve(input, 4)
}

function solve2(input) {
    return solve(input, 14)
}

module.exports = {solve1, solve2}
