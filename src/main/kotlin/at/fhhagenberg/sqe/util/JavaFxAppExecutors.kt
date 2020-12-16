package at.fhhagenberg.sqe.util

import at.fhhagenberg.sqe.AppExecutors
import javafx.application.Platform
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class JavaFxAppExecutors : AppExecutors(Executors.newSingleThreadExecutor(), MainThreadExecutor()) {

    private val networkIOExecutor: ExecutorService = networkIO as ExecutorService

    private class MainThreadExecutor : Executor {
        override fun execute(command: Runnable) {
            Platform.runLater(command)
        }
    }

    override fun shutdown() {
        networkIOExecutor.shutdown()
        try {
            networkIOExecutor.awaitTermination(10, TimeUnit.SECONDS)
        } catch (_: Exception) { }
    }
}