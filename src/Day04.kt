fun main() {

    fun parseBingoGame(input: List<String>): BingoGame {
        val draws = input[0].split(",").map { it.toInt() }
        val boardInputs =
            input.subList(1, input.size).chunked(6).map { boardLines -> boardLines.filter { it.isNotBlank() } }
        val boards = boardInputs.map { BingoBoard.fromString(it) }
        return BingoGame(draws, boards)
    }

    fun runGame(bingoGame: BingoGame): List<CompleteBoard> {
        var (draws, boards) = bingoGame
        val drawIterator = draws.iterator()
        val completedBoards = ArrayList<CompleteBoard>()
        var draw = 0
        while (boards.isNotEmpty()) {
            draw = drawIterator.next()
            boards.forEach {
                it.mark(draw)
                if (it.isComplete()) {
                    boards -= it
                    completedBoards += CompleteBoard(it, draw)
                }
            }
        }

        return completedBoards
    }

    fun part1(input: List<String>): Int {
        val completeBoard = runGame(parseBingoGame(input)).first()
        return completeBoard.bingoBoard.getScore(completeBoard.winningDraw)
    }

    fun part2(input: List<String>): Int {
        val completeBoard = runGame(parseBingoGame(input)).last()
        return completeBoard.bingoBoard.getScore(completeBoard.winningDraw)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("Day04")
    println(part1(input))

    check(part2(testInput) == 1924)
    println(part2(input))
}

private data class CompleteBoard(val bingoBoard: BingoBoard, val winningDraw: Int)
private data class BingoGame(val draws: List<Int>, val boards: List<BingoBoard>)
private data class BingoBoard(val rows: List<List<BingoEntry>>) {
    companion object {
        fun fromString(rows: List<String>): BingoBoard {
            return BingoBoard(rows.map { row ->
                row.split(" ").filter { it.isNotBlank() }.map { number -> BingoEntry(number.toInt()) }
            })
        }
    }

    fun mark(number: Int) {
        rows.forEach { bingoRow -> bingoRow.forEach { if (it.number == number) it.marked = true } }
    }

    fun isComplete(): Boolean {
        val columns = rows[0].indices
        return rows.any { bingoRow -> bingoRow.all { it.marked } } || columns.any { column -> rows.all { it[column].marked } }
    }


    fun getScore(winningNumber: Int): Int {
        return rows.sumOf { bingoRow ->
            bingoRow.filter { bingoEntry -> !bingoEntry.marked }.sumOf { bingoEntry -> bingoEntry.number }
        } * winningNumber
    }
}

private data class BingoEntry(val number: Int, var marked: Boolean = false)