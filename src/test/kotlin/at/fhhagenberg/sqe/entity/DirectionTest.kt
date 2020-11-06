package at.fhhagenberg.sqe.entity

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class DirectionTest {

    @Test
    fun testValidValueOf() {
        val direction = Direction.valueOf(0)
        assertEquals(Direction.UP, direction)
    }

    @Test
    fun testInvalidValueOf() {
        val direction = Direction.valueOf(-10)

        assertEquals(Direction.UNKNOWN, direction)
    }
}