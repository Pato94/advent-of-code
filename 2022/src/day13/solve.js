function isNumber(a) {
    return typeof a === 'number'
}

function compareLists(a, b) {
    let i = 0
    while (i < a.length && i < b.length) {
        const res = compare(a[i], b[i])
        if (res !== 0) {
            return res
        }
        i++
    }
    return Math.sign(b.length - a.length)
}

/**
 * returns 1 if the elements are in correct order
 * returns -1 if the elements are in wrong order
 * returns 0 if we need to continue checking
 */
function compare(a, b) {
    if (isNumber(a) && isNumber(b)) {
        return Math.sign(b - a)
    }
    if (isNumber(a)) a = [a]
    if (isNumber(b)) b = [b]
    return compareLists(a, b)
}

function solve1(input) {
    let sum = 0
    let i = 1
    let prev = undefined
    for (const line of input) {
        if (line === "") continue
        if (prev === undefined) {
            prev = eval(line)
            continue
        }
        if (compare(prev, eval(line)) > 0) sum += i
        prev = undefined
        i++
    }
    return sum
}

function solve2(input) {
    const packets = []
    for (const line of input) {
        if (line === "") continue
        packets.push(eval(line))
    }
    const a = [[2]]
    const b = [[6]]
    packets.push(a)
    packets.push(b)
    packets.sort((a, b) => -1 * compare(a, b))
    let mul = 1
    for (let i = 0; i < packets.length; i++) {
        const packet = packets[i]
        if (packet === a || packet === b) {
            mul *= (i + 1)
        }
    }
    return mul
}

module.exports = { compare, solve1, solve2 }
