function solve1(lines) {
    let max = Number.MIN_VALUE
    let count = 0
    for (let line of lines) {
        if (line === "") {
            if (count > max) {
                max = count
            }
            count = 0
        } else {
            const num = parseInt(line)
            count += num
        }
    }
    return max
}

function solve2(lines) {
    // apparently js does not have heaps
    let max1 = Number.MIN_VALUE
    let max2 = Number.MIN_VALUE
    let max3 = Number.MIN_VALUE
    let count = 0
    for (let line of lines) {
        if (line === "") {
            if (count > max1) {
                // swapping so we compare the old max1 with the current max2
                const aux = count
                count = max1
                max1 = aux
            }
            if (count > max2) {
                const aux = count
                count = max2
                max2 = aux
            }
            if (count > max3) {
                const aux = count
                count = max3
                max3 = aux
            }
            count = 0
        } else {
            const num = parseInt(line)
            count += num
        }
    }
    return max1 + max2 + max3
}

module.exports.solve1 = solve1
module.exports.solve2 = solve2
