package at.fhhagenberg.sqe.model

data class Error(
        val exception: Throwable,
        val errorCode: ErrorCode
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Error

        if (errorCode != other.errorCode) return false

        return true
    }

    override fun hashCode(): Int {
        return errorCode.hashCode()
    }
}