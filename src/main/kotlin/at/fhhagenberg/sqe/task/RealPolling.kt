package at.fhhagenberg.sqe.task

import at.fhhagenberg.sqe.di.key.PollingInterval
import com.google.inject.Inject
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class RealPolling @Inject constructor(
        @PollingInterval private val pollingInterval: Long,
        private val updateElevatorStoreTask: UpdateElevatorStoreTask
) : Polling {

    private val executorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    private var task: Future<*>? = null

    override fun start() {
        task = executorService.submit {
            while (!Thread.interrupted()) {
                val t1 = System.currentTimeMillis()
                updateElevatorStoreTask.fetchData()

                val operationTime = System.currentTimeMillis() - t1
                val sleepTime = (pollingInterval - operationTime).coerceAtLeast(0)

                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTime)
                } catch (_: InterruptedException) {
                    break
                }
            }
        }
    }

    override fun stop() {
        task?.cancel(true)
        executorService.shutdown()
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS)
        } catch (_: Exception) { }
    }
}