const fs = require('fs')
const { solve1, solve2 } = require('./solve')

const lines = fs.readFileSync('./input.txt').toString().split("\n")

console.log(solve1(lines))
console.log(solve2(lines))
