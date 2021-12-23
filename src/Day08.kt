import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        val searchedNumbersAsSegmentCount = listOf(2, 4, 3, 7)
        val elements = input.map {
            it.split("|").last().split(" ").map { s -> s.trim() }.filterNot { s -> s.isBlank() }.map { s -> s.length }
        }.map { list ->
            list.count { searchedNumbersAsSegmentCount.contains(it) }
        }
        return elements.sum()
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (uniqueSignalText, outPutText) = line.split("|")
            val uniqueSignals = uniqueSignalText.toSignals()
            val outPutSignals = outPutText.toSignals()
            val decoderMapping = uniqueSignals.createDecoderMapping()
            val outSignal = outPutSignals.mapIndexed { index, signal ->
                decoderMapping[signal]!!.intValue * (10.0.pow(3 - index).toInt())
            }.sum()
            outSignal
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)

    val input = readInput("Day08")
    println(part1(input))

    check(part2(testInput) == 61229)
    println(part2(input))
}

private fun List<List<Char>>.createDecoderMapping(): Map<List<Char>, Digit> {
    val decoderMap = HashMap<Digit, List<Char>>()
    decoderMap[Digit.ONE] = find { it.size == 2 }!!
    decoderMap[Digit.FOUR] = find { it.size == 4 }!!
    decoderMap[Digit.SEVEN] = find { it.size == 3 }!!
    decoderMap[Digit.EIGHT] = find { it.size == 7 }!!
    decoderMap[Digit.NINE] = find { it.size == 6 && it.containsAll(decoderMap[Digit.FOUR]!!) }!!
    decoderMap[Digit.ZERO] =
        find { it.size == 6 && !decoderMap.values.contains(it) && it.containsAll(decoderMap[Digit.ONE]!!) }!!
    decoderMap[Digit.SIX] = find { it.size == 6 && !decoderMap.values.contains(it) }!!
    decoderMap[Digit.THREE] = find { it.size == 5 && it.containsAll(decoderMap[Digit.SEVEN]!!) }!!
    decoderMap[Digit.FIVE] =
        find { it.size == 5 && !decoderMap.values.contains(it) && (decoderMap[Digit.SIX]!! - it.toSet()).size == 1 }!!
    decoderMap[Digit.TWO] = find { it.size == 5 && !decoderMap.values.contains(it) }!!
    return decoderMap.entries.associateBy({ it.value }) { it.key }
}

private fun String.toSignals(): List<List<Char>> {
    return split(" ").map { signal -> signal.trim() }.filterNot { it.isBlank() }.map { it.toCharArray().sorted() }
}

enum class Digit(val intValue: Int) {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9)
}