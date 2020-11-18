package at.fhhagenberg.sqe.util

import at.fhhagenberg.sqe.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(
        instant,
        instant
) {
    companion object {
        private val instant by lazy {
            Executor { it.run() }
        }
    }
}