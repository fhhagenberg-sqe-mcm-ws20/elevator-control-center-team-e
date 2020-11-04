package at.fhhagenberg.sqe.entity

import at.fhhagenberg.sqe.util.EnumUtils

enum class DoorState(val doorState: Int) {
    OPEN(1), CLOSED(2), OPENING(3), CLOSING(4);

    companion object {
        @JvmStatic
        fun valueOf(doorState: Int): DoorState? = EnumUtils.valOf { it.doorState == doorState }
    }
}