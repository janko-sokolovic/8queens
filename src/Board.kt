class Board {

    companion object {
        const val DIMENSION = 8
    }

    private val fields: Array<Array<FieldType>>

    var foundSolutions: MutableList<Board> = mutableListOf()
        private set

    constructor() {
        fields = Array(DIMENSION) { kotlin.Array(DIMENSION) { FieldType.EMPTY } }
    }

    constructor(toTypedArray: Array<Array<FieldType>>) {
        fields = toTypedArray
    }

    fun doIt() {
        doFor(this.clone(), 0)
    }

    private fun doFor(board: Board, y: Int) {
        if (y == DIMENSION) {
            this.foundSolutions.add(board.clone())
            return
        }

        for (i in 0 until DIMENSION) {

            val cloneBoard = board.clone()

            if (cloneBoard.isFieldAvailable(i, y)) {
                cloneBoard.putQueenAt(i, y)
                doFor(cloneBoard, y + 1)
            }
        }
    }


    fun isFieldAvailable(x: Int, y: Int): Boolean {
        return fields[y][x] == FieldType.EMPTY
    }

    fun getFieldAt(x: Int, y: Int) = fields[y][x]

    fun putQueenAt(x: Int, y: Int) {
        fields[y][x] = FieldType.QUEEN

        attackDiagonal(x, y)
        attackOrthogonal(x, y)
    }

    private fun attackDiagonal(x: Int, y: Int) {
        //go up and left
        var tempX = x - 1
        var tempY = y - 1
        while (tempX >= 0 && tempY >= 0) {
            fields[tempY--][tempX--] = FieldType.ATTACKED
        }

        //go down and right
        tempX = x + 1
        tempY = y + 1
        while (tempX < DIMENSION && tempY < DIMENSION) {
            fields[tempY++][tempX++] = FieldType.ATTACKED
        }

        //go down and left
        tempX = x - 1
        tempY = y + 1
        while (tempX >= 0 && tempY < DIMENSION) {
            fields[tempY++][tempX--] = FieldType.ATTACKED
        }

        //go up and right
        tempX = x + 1
        tempY = y - 1
        while (tempX < DIMENSION && tempY >= 0) {
            fields[tempY--][tempX++] = FieldType.ATTACKED
        }
    }

    private fun attackOrthogonal(x: Int, y: Int) {
        //go horizontal
        var tempX = 0
        while (tempX < DIMENSION) {
            if (tempX != x) {
                fields[y][tempX] = FieldType.ATTACKED
            }
            tempX++
        }

        //go vertical
        var tempY = 0
        while (tempY < DIMENSION) {
            if (tempY != y) {
                fields[tempY][x] = FieldType.ATTACKED
            }
            tempY++
        }
    }

    override fun toString(): String {
        var result = ""
        for (row in fields) {
            for (field in row) {
                result += when (field) {
                    FieldType.EMPTY -> " O "
                    FieldType.QUEEN -> " Q "
                    FieldType.ATTACKED -> " X "
                }
            }
            result += System.lineSeparator()
        }
        result += System.lineSeparator()
        return result
    }

    fun clone(): Board {
        return Board(fields.map { it.clone() }.toTypedArray())
    }

}

enum class FieldType {
    EMPTY, QUEEN, ATTACKED
}
