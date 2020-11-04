package at.fhhagenberg.sqe.entity

import at.fhhagenberg.sqe.util.EnumUtils.valOf

enum class Direction(val direction: Int) {
    UP(0), DOWN(1), UNCOMMITTED(2);

    companion object {
        @JvmStatic
        fun valueOf(direction: Int): Direction? = valOf { it.direction == direction }
    }
}