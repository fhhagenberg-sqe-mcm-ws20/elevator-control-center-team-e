package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.AppExecutors
import at.fhhagenberg.sqe.di.PollingInterval
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.entity.ServicedFloor
import at.fhhagenberg.sqe.model.Resource
import com.google.inject.Inject
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

typealias UpdateListener = (Resource<ElevatorControlSystem>) -> Unit

class ElevatorStore @Inject constructor(
        private val appExecutors: AppExecutors,
        private val elevatorRepository: ElevatorRepository,
        @PollingInterval private val pollingInterval: Long
) : Runnable {

    private val updateListeners = mutableListOf<WeakReference<UpdateListener>>()

    @Synchronized
    fun addUpdateListener(listener: UpdateListener) {
        updateListeners.add(WeakReference(listener))
    }

    @Synchronized
    fun removeUpdateListener(listener: UpdateListener) {
        updateListeners.removeAll { it.get() === listener }
    }

    fun updateCommittedDirection(elevator: Elevator, listener: ((Resource<Boolean>) -> Unit)?) {
        callUpdateFunction({
            elevatorRepository.updateCommittedDirection(elevator)
        }, listener)
    }

    fun updateServicedFloor(servicedFloor: ServicedFloor, listener: ((Resource<Boolean>) -> Unit)?) {
        callUpdateFunction({
            elevatorRepository.updateServicedFloor(servicedFloor)
        }, listener)
    }

    fun updateTargetFloor(elevator: Elevator, listener: ((Resource<Boolean>) -> Unit)?) {
        callUpdateFunction({
            elevatorRepository.updateTargetFloor(elevator)
        }, listener)
    }

    override fun run() {
        while (!Thread.interrupted()) {
            // Measure time before polling
            val t1 = System.currentTimeMillis()

            // Run polling
            poll()

            // Measure time after polling
            val t2 = System.currentTimeMillis()

            // Calculate remaining sleep time after knowing time consumed by operations
            val sleepTime = pollingInterval - (t2 - t1)

            // Sleep
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTime)
            } catch (e: InterruptedException) {
                break
            }
        }
    }

    private fun poll() {
        val elevatorControlSystemResource = elevatorRepository.getElevatorControlSystem()
        notifyUpdateListeners(elevatorControlSystemResource)
    }

    @Synchronized
    private fun notifyUpdateListeners(elevatorControlSystemResource: Resource<ElevatorControlSystem>) {
        updateListeners.forEach {
            appExecutors.mainThread.execute {
                it.get()?.invoke(elevatorControlSystemResource)
            }
        }
        updateListeners.removeIf { it.get() == null }
    }

    private fun callUpdateFunction(functionCall: () -> Resource<Boolean>, listener: ((Resource<Boolean>) -> Unit)?) {
        appExecutors.networkIO.execute {
            val result = functionCall.invoke()
            listener?.let { listener ->
                appExecutors.mainThread.execute {
                    listener.invoke(result)
                }
            }
        }
    }
}