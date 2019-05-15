import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NQueenTest {

    private lateinit var board: Board

    @BeforeEach
    fun setup() {
        board = Board()
    }

    @Test
    fun `given empty board, is field available, return true`() {
        //when
        val fieldAvailable: Boolean = board.isFieldAvailable(2, 3)

        //then
        Assertions.assertTrue(fieldAvailable)
    }

    @Test
    fun `given empty board, when queen is placed then this field is unavailable`() {

        board.putQueenAt(0,0)

        assertFalse { board.isFieldAvailable(0, 0) }
    }

    @Test
    fun `when queen is placed then attacked fields are unavailable`() {
        board.putQueenAt(1,1)

        assertFalse { board.isFieldAvailable(0,0) }
        assertFalse { board.isFieldAvailable(0,1) }
        assertFalse { board.isFieldAvailable(1,0) }
        assertFalse { board.isFieldAvailable(2,1) }
        assertFalse { board.isFieldAvailable(2,0) }
        assertFalse { board.isFieldAvailable(1,2) }
        assertFalse { board.isFieldAvailable(2,2) }
        assertFalse { board.isFieldAvailable(0,2) }
        assertFalse { board.isFieldAvailable(1,7) }
        assertFalse { board.isFieldAvailable(7,7) }
        assertFalse { board.isFieldAvailable(7,1) }
    }

    @Test
    fun `given board, when clone, initial is not mutated` (){

        board.putQueenAt(2,2)

        val newBoard = board.clone()

        newBoard.putQueenAt(4,3)

        assertTrue { board.isFieldAvailable(4,3) }

    }

    @Test
    fun `place two queens, do not override first`(){
        board.putQueenAt(2,2)
        board.putQueenAt(4,3)

        assertEquals(FieldType.QUEEN, board.getFieldAt(2,2))
        assertEquals(FieldType.QUEEN, board.getFieldAt(4,3))
    }

    @Test
    fun `given queen in corner, when putNextQueen, then put in first available field` () {

        // When
        board.doIt()

        // Then
        // Empirically known number of results for n=8 queens is 92!
        assertEquals(92, board.foundSolutions.size)

        // For purpose of random check print out single result
        println("Random result number ${34}: \n")
        println(board.foundSolutions[34])
    }
}