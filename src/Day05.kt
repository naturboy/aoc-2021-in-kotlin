import kotlin.math.sqrt

fun main() {
    fun parseToLines(input: List<String>): List<Line> {
        return input.map { line ->
            val (x1, y1, x2, y2) = line.split(" -> ", ",").map { it.toInt() }
            Line(Point(x1, y1), Point(x2, y2))
        }
    }

    fun part1(input: List<String>): Int {
        val lines = parseToLines(input).filterNot { it.isDiagonal() }
        val points = lines.map { it.toPoints() }.flatten().groupingBy { it }.eachCount()
        return points.filter { it.value >= 2 }.size
    }

    fun part2(input: List<String>): Int {
        return parseToLines(input).map { it.toPoints() }.flatten().groupingBy { it }.eachCount().filter { it.value >= 2 }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)

    val input = readInput("Day05")
    println(part1(input))

    check(part2(testInput) == 12)
    println(part2(input))
}

private data class Line(val pointOne: Point, val pointTwo: Point) {
    fun toPoints(): List<Point> {
        if (isDiagonal()){
            return buildList {
                for ((x, y) in pointOne.x.towards(pointTwo.x).zip(pointOne.y.towards(pointTwo.y))){
                    add(Point(x, y))
                }
            }
        }else{
            return buildList {
                for (x in pointOne.x.towards(pointTwo.x)) {
                    for (y in pointOne.y.towards(pointTwo.y)) {
                        add(Point(x, y))
                    }
                }
            }
        }
    }

    fun isDiagonal() = pointOne.x != pointTwo.x && pointOne.y != pointTwo.y
}

fun Int.towards(end: Int): IntProgression {
    val step = if (end > this) 1 else -1
    return IntProgression.fromClosedRange(this,end, step)
}

private data class Point(val x: Int, val y: Int)