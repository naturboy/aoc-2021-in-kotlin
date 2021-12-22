typealias FishPopulation = Map<Int, Long>

fun main() {
    fun simulate(fishPopulation: FishPopulation, days: Int): FishPopulation{
        var currentPopulation = fishPopulation
        for (day in 1..days){
            val newPopulation = HashMap<Int, Long>()
            currentPopulation.forEach() {
                val newValue = it.key - 1
                if (newValue < 0) {
                    newPopulation[8] = it.value
                    newPopulation[6] = newPopulation.getOrDefault(6, 0) + it.value
                }else{
                    newPopulation[newValue] = newPopulation.getOrDefault(newValue, 0) + it.value
                }
            }
            currentPopulation = newPopulation
        }
        return currentPopulation
    }

    fun part1(input: List<String>): Long {
        var initialPopulation = input.first().split(",").map { it.toInt() }.groupingBy { it }.eachCount()
        val endPopulation = simulate(initialPopulation.mapValues { it.value.toLong() }, 80)
        return endPopulation.values.sumOf { it }
    }

    fun part2(input: List<String>): Long {
        var initialPopulation = input.first().split(",").map { it.toInt() }.groupingBy { it }.eachCount()
        val endPopulation = simulate(initialPopulation.mapValues { it.value.toLong() }, 256)
        return endPopulation.values.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)

    val input = readInput("Day06")
    println(part1(input))

    check(part2(testInput) == 26984457539L)
    println(part2(input))
}
