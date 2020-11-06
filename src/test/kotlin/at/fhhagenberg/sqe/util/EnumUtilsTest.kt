package at.fhhagenberg.sqe.util

import at.fhhagenberg.sqe.entity.Direction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EnumUtilsTest {
    @Test
    fun testEnumUtils() {
        assertEquals(Direction.DOWN, EnumUtils.valOf<Direction> { it.direction == Direction.DOWN.direction })
        assertEquals(null, EnumUtils.valOf<Direction> { it.direction == -10 })
    }
}