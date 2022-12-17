function range(input) {
    return input.split("-").map(it => parseInt(it))
}

function size(range) {
    const [start, end] = range
    return end - start
}

function solve1(input) {
    let count = 0
    for (const line of input) {
        if (line === "") continue
        const [r1, r2] = line.split(",").map(it => range(it))
        const [big, small] = (size(r1) > size(r2)) ? [r1, r2] : [r2, r1]
        if (big[0] <= small[0] && big[1] >= small[1]) {
            count++
        }
    }
    return count
}

function includes(num, range) {
    return num >= range[0] && num <= range[1]
}

function overlap(range1, range2) {
    return includes(range1[1], range2) || includes(range2[1], range1)
}

function solve2(input) {
    let count = 0
    for (const line of input) {
        if (line === "") continue
        const [r1, r2] = line.split(",").map(it => range(it))
        if (overlap(r1, r2)) {
            count++
        }
    }
    return count
}

module.exports = { range, size, solve1, solve2 }
