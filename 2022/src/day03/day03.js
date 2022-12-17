function findDuplicated(rucksack) {
    const first = rucksack.substring(0, rucksack.length / 2)
    const second = rucksack.substring(rucksack.length / 2)
    for (const e of first) {
        if (second.indexOf(e) !== -1) {
            return e
        }
    }
    throw new Error(`No duplicated founds for sack ${rucksack}`)
}

function priority(type) {
    if (type >= "a" && type <= "z") {
        return type.charCodeAt(0) - "a".charCodeAt(0) + 1
    }
    if (type >= "A" && type <= "Z") {
        return type.charCodeAt(0) - "A".charCodeAt(0) + 27
    }
}

function solve1(input) {
    let score = 0
    for (const line of input) {
        if (line === "") continue
        const dup = findDuplicated(line)
        score += priority(dup)
    }
    return score
}

function findBadge(rs1, rs2, rs3) {
    for (const b of rs1) {
        if (rs2.indexOf(b) !== -1) {
            if (rs3.indexOf(b) !== -1) {
                return b
            }
        }
    }
    throw new Error(`No badge found for group ${rs1 + rs2 + rs3}`)
}

function solve2(input) {
    let score = 0
    let group = []
    for (const line of input) {
        if (line === "") continue
        group.push(line)
        if (group.length === 3) {
            score += priority(findBadge(...group))
            group = []
        }
    }
    return score
}

module.exports = { findDuplicated, priority, solve1, findBadge, solve2 }
