import java.util.*

fun main() {

    fun part1(input: List<SubmarineMovement>): Int {
        val finalPosition = input.fold(SubmarinePosition(0, 0, 0), SubmarinePosition::moveBasic)
        return finalPosition.vertical * finalPosition.horizontal
    }

    fun part2(input: List<SubmarineMovement>): Int {
        val finalPosition = input.fold(SubmarinePosition(0, 0, 0), SubmarinePosition::moveAdvanced)
        return finalPosition.vertical * finalPosition.horizontal
    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test").map { SubmarineMovement.fromString(it) }
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02").map { SubmarineMovement.fromString(it) }
    println(part1(input))
    println(part2(input))
}

enum class SubmarineMovementDirection {
    FORWARD, DOWN, UP
}

data class SubmarinePosition(val vertical: Int, val horizontal: Int, val aim: Int = 0) {
    fun moveBasic(movement: SubmarineMovement): SubmarinePosition {
        return when (movement.direction) {
            SubmarineMovementDirection.DOWN -> copy(vertical = vertical + movement.units)
            SubmarineMovementDirection.UP -> copy(vertical = vertical - movement.units)
            SubmarineMovementDirection.FORWARD -> copy(horizontal = horizontal + movement.units)
        }
    }

    fun moveAdvanced(movement: SubmarineMovement): SubmarinePosition {
        return when (movement.direction) {
            SubmarineMovementDirection.DOWN -> copy(aim = aim + movement.units)
            SubmarineMovementDirection.UP -> copy(aim = aim - movement.units)
            SubmarineMovementDirection.FORWARD -> copy(
                vertical = vertical + (aim * movement.units),
                horizontal = horizontal + movement.units,
            )
        }
    }
}

data class SubmarineMovement(val direction: SubmarineMovementDirection, val units: Int) {
    companion object {
        fun fromString(instruction: String): SubmarineMovement {
            val (direction, units) = instruction.split(" ")
            return SubmarineMovement(SubmarineMovementDirection.valueOf(direction.uppercase(Locale.getDefault())), units.toInt())
        }
    }
}

