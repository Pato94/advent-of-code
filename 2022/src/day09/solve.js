function shouldUpdate(head, tail) {
    const deltaX = Math.abs(head.x - tail.x);
    const deltaY = Math.abs(head.y - tail.y);
    return Math.max(deltaX, deltaY) > 1
}

function normalize(delta) {
    return delta === 0 ? 0 : delta / Math.abs(delta)
}

function update(head, tail) {
    if (shouldUpdate(head, tail)) {
        tail.x = tail.x + normalize(head.x - tail.x)
        tail.y = tail.y + normalize(head.y - tail.y)
    }
    return `x${tail.x}y${tail.y}`
}

function apply(fn, times) {
    for (let i = 0; i < times; i++) fn();
}

function solve1(input) {
    const positions = new Set()

    const head = {x: 0, y: 0}
    const tail = {x: 0, y: 0}

    for (const line of input) {
        if (line === "") continue

        let [_, dir, count] = /(\w) (\d+)/.exec(line)
        count = parseInt(count)
        switch (dir) {
            case "R":
                apply(() => {
                    head.x += 1;
                    positions.add(update(head, tail))
                }, count);
                break;
            case "U":
                apply(() => {
                    head.y -= 1;
                    positions.add(update(head, tail))
                }, count);
                break;
            case "L":
                apply(() => {
                    head.x -= 1;
                    positions.add(update(head, tail))
                }, count);
                break;
            case "D":
                apply(() => {
                    head.y += 1;
                    positions.add(update(head, tail))
                }, count);
                break;
            default:
                break;
        }
    }
    return positions.size
}

function solve2(input) {
    const positions = new Set()

    const rope = Array(10).fill().map(() => ({x: 0, y: 0}))

    for (const line of input) {
        if (line === "") continue

        let [_, dir, count] = /(\w) (\d+)/.exec(line)
        count = parseInt(count)
        switch (dir) {
            case "R":
                apply(() => {
                    let head = rope[0]
                    head.x += 1;
                    let key = ""
                    for (let i = 0; i < 9; i++) {
                        key = update(head, rope[i+1])
                        head = rope[i+1]
                    }
                    positions.add(key)
                }, count);
                break;
            case "U":
                apply(() => {
                    let head = rope[0]
                    head.y -= 1;
                    let key = ""
                    for (let i = 0; i < 9; i++) {
                        key = update(head, rope[i+1])
                        head = rope[i+1]
                    }
                    positions.add(key)
                }, count);
                break;
            case "L":
                apply(() => {
                    let head = rope[0]
                    head.x -= 1;
                    let key = ""
                    for (let i = 0; i < 9; i++) {
                        key = update(head, rope[i+1])
                        head = rope[i+1]
                    }
                    positions.add(key)
                }, count);
                break;
            case "D":
                apply(() => {
                    let head = rope[0]
                    head.y += 1;
                    let key = ""
                    for (let i = 0; i < 9; i++) {
                        key = update(head, rope[i+1])
                        head = rope[i+1]
                    }
                    positions.add(key)
                }, count);
                break;
            default:
                break;
        }
    }
    return positions.size
}

module.exports = {shouldUpdate, update, solve1, solve2}
