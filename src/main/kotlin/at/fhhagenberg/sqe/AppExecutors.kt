package at.fhhagenberg.sqe

import java.util.concurrent.Executor

open class AppExecutors(
        val networkIO: Executor,
        val mainThread: Executor
)