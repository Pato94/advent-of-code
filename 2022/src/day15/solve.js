function mergeRanges(ranges) {
    ranges.sort((a, b) => a[0] - b[0])
    let result = []
    let prev = ranges[0]
    for (let i = 1; i < ranges.length; i++) {
        if (prev[1] >= ranges[i][0]) {
            prev = [prev[0], Math.max(ranges[i][1], prev[1])];
        } else {
            result.push(prev);
            prev = ranges[i];
        }
    }
    result.push(prev);
    return result;
}

function getExcluded(sensorsAndBeacons, rowToCheck) {
    let excluded = []
    for (const sensorAndBeacon of sensorsAndBeacons) {
        const [[sx, sy], [bx, by]] = sensorAndBeacon
        const distanceToBeacon = Math.abs(sx - bx) + Math.abs(sy - by)
        const distanceToRow = Math.abs(sy - rowToCheck)
        const nAffected = distanceToBeacon - distanceToRow
        if (nAffected > 0) {
            excluded.push([sx - nAffected, sx + nAffected])
        }
    }

    return mergeRanges(excluded)
}

function solve1(input, rowToCheck) {
    const sensorsAndBeacons = []
    let uniqueBeaconsInRowToCheck = []
    for (const line of input) {
        if (line === "") continue
        const regex = /Sensor at x=([\-0-9]+), y=([\-0-9]+): closest beacon is at x=([\-0-9]+), y=([\-0-9]+)/
        const [_, sx, sy, bx, by] = regex.exec(line).map(it => parseInt(it))
        if (by === rowToCheck) {
            if (uniqueBeaconsInRowToCheck.indexOf(bx) < 0) {
                uniqueBeaconsInRowToCheck.push(bx)
            }
        }
        sensorsAndBeacons.push([[sx, sy], [bx, by]])
    }

    let count = getExcluded(sensorsAndBeacons, rowToCheck).reduceRight((p, c) => p + c[1] - c[0] + 1, 0)

    return count - uniqueBeaconsInRowToCheck.length;
}

function solve2(input, end) {
    const sensorsAndBeacons = []
    for (const line of input) {
        if (line === "") continue
        const regex = /Sensor at x=([\-0-9]+), y=([\-0-9]+): closest beacon is at x=([\-0-9]+), y=([\-0-9]+)/
        const [_, sx, sy, bx, by] = regex.exec(line).map(it => parseInt(it))
        sensorsAndBeacons.push([[sx, sy], [bx, by]])
    }
    for (let i = 0; i < end; i++) {
        const e = getExcluded(sensorsAndBeacons, i)
        if (e.length > 1) {
            return (e[0][1] + 1) * 4000000 + i
        }
    }
}

module.exports = {solve1, solve2}
