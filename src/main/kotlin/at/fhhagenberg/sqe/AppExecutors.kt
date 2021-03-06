package at.fhhagenberg.sqe

import java.util.concurrent.Executor

abstract class AppExecutors(
        val networkIO: Executor,
        val mainThread: Executor
) {
    abstract fun shutdown()
}