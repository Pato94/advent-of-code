function solve1(input) {
    let x = 1
    let sum = 0
    let cycles = 1
    const increaseCycle = () => {
        if ((cycles - 20) % 40 === 0) {
            // update sum
            sum += x * cycles
        }
        cycles++
    }
    for (const line of input) {
        if (line === "") continue

        if (line === "noop") {
            increaseCycle()
            continue
        }

        // str is "addx -10", trimming 5 starting characters to parse the number
        const amount = parseInt(line.substring(5))
        increaseCycle()
        increaseCycle()
        x += amount
    }
    return sum
}

function solve2(input) {
    let x = 1
    let pic = []
    let cycles = 1
    const increaseCycle = () => {
        const pos = (cycles - 1) % 40
        if (pos >= x - 1 && pos <= x + 1) {
            pic.push("#")
        } else {
            pic.push(".")
        }
        cycles++
    }
    for (const line of input) {
        if (line === "") continue

        if (line === "noop") {
            increaseCycle()
            continue
        }

        // str is "addx -10", trimming 5 starting characters to parse the number
        const amount = parseInt(line.substring(5))
        increaseCycle()
        increaseCycle()
        x += amount
    }
    return pic.join("").split(/(?<=^(?:.{40})+)/).join("\n")
}

module.exports = {solve1, solve2}
