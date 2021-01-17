package at.fhhagenberg.sqe.task

import at.fhhagenberg.sqe.adapter.ElevatorAdapter
import at.fhhagenberg.sqe.di.key.AutoModeProperty
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.store.ElevatorStore
import com.google.inject.Inject
import javafx.beans.property.BooleanProperty

class AutoModeTask @Inject constructor(
    private val elevatorAdapter: ElevatorAdapter,
    private val elevatorStore: ElevatorStore,
    @AutoModeProperty val autoModeProperty: BooleanProperty
) : Runnable {

    override fun run() {
        if (autoModeProperty.get()) {
            elevatorStore.getElevatorControlSystem().get()?.data?.let { elevatorControlSystem ->
                executeAutoModeLogic(elevatorControlSystem)
            }
        }
    }

    private fun executeAutoModeLogic(elevatorControlSystem: ElevatorControlSystem) {
        // TODO
    }
}