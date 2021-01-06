package at.fhhagenberg.sqe.model

data class AppState(
    val status: Status,
    val error: Error?
)