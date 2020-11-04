package at.fhhagenberg.sqe.entity

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class DoorStateTest {
    @Test
    fun testValidValueOf() {
        val doorState = DoorState.valueOf(1)
        assertEquals(DoorState.OPEN, doorState)
    }

    @Test
    fun testInvalidValueOf() {
        val doorState = DoorState.valueOf(-10)
        assertEquals(DoorState.UNKNOWN, doorState)
    }
}