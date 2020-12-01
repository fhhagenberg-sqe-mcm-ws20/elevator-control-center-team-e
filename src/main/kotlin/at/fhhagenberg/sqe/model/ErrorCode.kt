package at.fhhagenberg.sqe.model

import at.fhhagenberg.sqe.util.EnumUtils

enum class ErrorCode(val errorCode: Int) {
    UNKNOWN(-1), CONNECTION_ERROR(0);

    companion object {
        @JvmStatic
        fun valueOf(errorCode: Int): ErrorCode = EnumUtils.valOf { it.errorCode == errorCode } ?: UNKNOWN
    }
}