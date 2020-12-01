package at.fhhagenberg.sqe.model

data class Error(
        val exception: Throwable,
        val errorCode: ErrorCode
)