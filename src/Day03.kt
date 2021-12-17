fun main() {

    fun part1(input: List<String>): Int {
        val gamma = input.gamma()
        val epsilon = gamma.invertBinary()
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String>): Int {
        fun rating(filter: (commonBit: Char, bit: Char) -> Boolean): String {
            var valuesLeft = input
            var column = 0
            while (valuesLeft.size > 1){
                val (zeroes, ones) = valuesLeft.bitCount(column)
                val commonBit = if (zeroes > ones) '0' else '1'
                valuesLeft = valuesLeft.filter { filter(commonBit, it[column]) }
                column++
            }
            return valuesLeft.first()
        }

        val oxygen = rating { commonBit, bit -> commonBit == bit }
        val co2 = rating { commonBit, bit -> commonBit != bit }
        return oxygen.toInt(2) * co2.toInt(2)
    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("Day03")
    println(part1(input))
    check(part2(testInput) == 230)
    println(part2(input))
}

private fun List<String>.gamma(): String {
    return this[0].indices.joinToString("") {
        val (zeroes, ones) = bitCount(it)
        (if (zeroes > ones) "0" else "1")
    }
}

private fun String.invertBinary(): String = this.asIterable().joinToString("") { if (it == '0') "1" else "0" }

private data class BitCount(val zeros: Int, val ones: Int)

private fun List<String>.bitCount(pos: Int): BitCount {
    val zeros = count { it[pos] == '0' }
    return BitCount(zeros, size - zeros)
}