import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val crabPositions = input.first().split(",").map { it.toInt() }
        var currentBest = crabPositions.average().toInt()
        var currentFuelCost = crabPositions.getFuelCost(currentBest)
        while (currentFuelCost > crabPositions.getFuelCost(currentBest+1) ) {
            currentBest++
            currentFuelCost = crabPositions.getFuelCost(currentBest)
        }
        while (currentFuelCost > crabPositions.getFuelCost(currentBest-1) ) {
            currentBest--
            currentFuelCost = crabPositions.getFuelCost(currentBest)
        }
        return currentFuelCost
    }

    fun part2(input: List<String>): Int {
        val crabPositions = input.first().split(",").map { it.toInt() }
        var currentBest = crabPositions.average().toInt()
        var currentFuelCost = crabPositions.getAdvancedFuelCost(currentBest)
        while (currentFuelCost > crabPositions.getAdvancedFuelCost(currentBest+1) ) {
            currentBest++
            currentFuelCost = crabPositions.getAdvancedFuelCost(currentBest)
        }
        while (currentFuelCost > crabPositions.getAdvancedFuelCost(currentBest-1) ) {
            currentBest--
            currentFuelCost = crabPositions.getAdvancedFuelCost(currentBest)
        }
        return currentFuelCost
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)

    val input = readInput("Day07")
    println(part1(input))

    check(part2(testInput) == 168)
    println(part2(input))
}

private fun List<Int>.getFuelCost(currentBest: Int): Int {
    return sumOf { abs(it - currentBest) }
}

private fun List<Int>.getAdvancedFuelCost(currentBest: Int): Int {
    return map { abs(it - currentBest) }.sumOf { it * (it+1) / 2 }
}