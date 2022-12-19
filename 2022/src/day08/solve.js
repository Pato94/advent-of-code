function visibleFromTop(grid, i, j) {
    let count = 0
    for (let k = i - 1; k >= 0; k--) {
        count++
        if (grid[i][j] <= grid[k][j]) return count
    }
    return count
}

function visibleFromLeft(grid, i, j) {
    let count = 0
    for (let k = j - 1; k >= 0; k--) {
        count++
        if (grid[i][j] <= grid[i][k]) return count
    }
    return count
}

function visibleFromBottom(grid, i, j) {
    let count = 0
    for (let k = i + 1; k < grid.length; k++) {
        count++
        if (grid[i][j] <= grid[k][j]) return count
    }
    return count
}

function visibleFromRight(grid, i, j) {
    let count = 0
    for (let k = j + 1; k < grid[i].length; k++) {
        count++
        if (grid[i][j] <= grid[i][k]) return count
    }
    return count
}

function isVisible(grid, i, j) {
    if (visibleFromTop(grid, i, j) === i) {
        return true
    }

    if (visibleFromLeft(grid, i, j) === j) {
        return true
    }

    if (visibleFromRight(grid, i, j) === (grid[i].length - j - 1)) {
        return true
    }

    return visibleFromBottom(grid, i, j) === (grid.length - i - 1)
}

function solve1(input) {
    const grid = []
    for (const line of input) {
        if (line === "") continue
        grid.push(line.split("").map(it => parseInt(it)))
    }
    let count = 0
    for (let i = 1; i < grid.length - 1; i++) {
        for (let j = 1; j < grid[i].length - 1; j++) {
            if (isVisible(grid, i, j)) {
                count++
            }
        }
    }
    count += grid.length * 2
    count += (grid[0].length - 2) * 2
    return count
}

function score(grid, i, j) {
    return visibleFromTop(grid, i, j) * visibleFromRight(grid, i, j) * visibleFromBottom(grid, i, j) * visibleFromLeft(grid, i, j)
}

function solve2(input) {
    const grid = []
    for (const line of input) {
        if (line === "") continue
        grid.push(line.split("").map(it => parseInt(it)))
    }
    let max = Number.MIN_VALUE
    for (let i = 1; i < grid.length - 1; i++) {
        for (let j = 1; j < grid[i].length - 1; j++) {
            const current = score(grid, i, j);
            if (current > max) {
                max = current
            }
        }
    }
    return max
}

module.exports = {visibleFromTop, visibleFromLeft, visibleFromRight, visibleFromBottom, isVisible, solve1, solve2}
