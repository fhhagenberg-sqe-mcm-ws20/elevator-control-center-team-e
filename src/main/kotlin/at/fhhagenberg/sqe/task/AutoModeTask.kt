package at.fhhagenberg.sqe.task

import at.fhhagenberg.sqe.adapter.ElevatorAdapter
import at.fhhagenberg.sqe.di.key.AutoModeProperty
import at.fhhagenberg.sqe.entity.DoorState
import at.fhhagenberg.sqe.entity.Elevator
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
        val requestedFloors = elevatorControlSystem.floors
            .filter { it.isDownActive || it.isUpActive }
            .toMutableList()

        for (elevator in elevatorControlSystem.elevators) {
            val targetFloorButton = elevator.buttons.firstOrNull { it.isActive }
            if (targetFloorButton != null) {
                setTargetFloor(elevator, targetFloorButton.floorNumber)
            } else {
                requestedFloors.firstOrNull()?.let { nextRequestedFloor ->
                    requestedFloors.remove(nextRequestedFloor)
                    setTargetFloor(elevator, nextRequestedFloor.floorNumber)
                }
            }
        }
    }

    private fun setTargetFloor(elevator: Elevator, targetFloor: Int) {
        if (elevator.doorState == DoorState.CLOSED || elevator.doorState == DoorState.OPEN) {
            elevator.targetFloor = targetFloor
            elevatorAdapter.updateTargetFloor(elevator, elevator.targetFloor)
        }
    }
}