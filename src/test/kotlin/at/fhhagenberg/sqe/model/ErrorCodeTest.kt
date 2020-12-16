package at.fhhagenberg.sqe.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ErrorCodeTest {

    @Test
    fun testValidValueOf() {
        val errorCode = ErrorCode.valueOf(0)
        assertEquals(ErrorCode.CONNECTION_ERROR, errorCode)
    }

    @Test
    fun testInvalidValueOf() {
        val errorCode = ErrorCode.valueOf(-10)

        assertEquals(ErrorCode.UNKNOWN, errorCode)
    }
}