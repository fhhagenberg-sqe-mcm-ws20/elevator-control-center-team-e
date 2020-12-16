package at.fhhagenberg.sqe.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.util.*

class ErrorTest {

    @Test
    fun testEquals() {
        val exc1 = Exception("Exception1")
        val error1 = Error(exc1, ErrorCode.CONNECTION_ERROR)
        val error2 = Error(Exception("Exception2"), ErrorCode.CONNECTION_ERROR)
        val error3 = Error(Exception("Exception3"), ErrorCode.UNKNOWN)

        assertEquals(exc1, error1.exception)
        assertEquals(ErrorCode.CONNECTION_ERROR, error1.errorCode)
        assertEquals(error1, error2)
        assertEquals(error1, error1)
        assertNotEquals(error1, error3)
        assertNotEquals(error1, Date())
        assertNotEquals(Date(), error1)
        assertNotEquals(error1, null)
        assertNotEquals(null, error1)
    }

    @Test
    fun testHashCode() {
        val error1 = Error(Exception("Exception1"), ErrorCode.CONNECTION_ERROR)
        val error2 = Error(Exception("Exception2"), ErrorCode.CONNECTION_ERROR)
        val error3 = Error(Exception("Exception3"), ErrorCode.UNKNOWN)

        assertEquals(error1.hashCode(), ErrorCode.CONNECTION_ERROR.hashCode())
        assertEquals(error1.hashCode(), error2.hashCode())
        assertEquals(error1.hashCode(), error1.hashCode())
        assertNotEquals(error1.hashCode(), error3.hashCode())
    }
}