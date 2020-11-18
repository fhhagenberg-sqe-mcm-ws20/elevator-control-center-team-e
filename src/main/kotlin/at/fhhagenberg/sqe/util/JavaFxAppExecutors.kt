package at.fhhagenberg.sqe.util

import at.fhhagenberg.sqe.AppExecutors
import javafx.application.Platform
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class JavaFxAppExecutors : AppExecutors(
        Executors.newSingleThreadExecutor(),
        MainThreadExecutor()
) {
    private class MainThreadExecutor : Executor {
        override fun execute(command: Runnable) {
            Platform.runLater(command)
        }
    }
}