const fs = require('fs')
const { solve1 } = require('./day02')

const lines = fs.readFileSync('./input.txt').toString().split("\n")

console.log(solve1(lines))
