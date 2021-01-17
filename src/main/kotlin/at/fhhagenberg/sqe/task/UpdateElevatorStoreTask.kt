package at.fhhagenberg.sqe.task

import at.fhhagenberg.sqe.AppExecutors
import at.fhhagenberg.sqe.adapter.ElevatorAdapter
import at.fhhagenberg.sqe.store.ElevatorStore
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.model.Resource
import com.google.inject.Inject

class UpdateElevatorStoreTask @Inject constructor(
        private val appExecutors: AppExecutors,
        private val elevatorStore: ElevatorStore,
        private val elevatorAdapter: ElevatorAdapter
) : Runnable {

    override fun run() {
        val elevatorControlSystemResource = elevatorAdapter.getElevatorControlSystem()
        appExecutors.mainThread.execute {
            updateStore(elevatorControlSystemResource)
        }
    }

    private fun updateStore(elevatorControlSystemResource: Resource<ElevatorControlSystem>) {
        elevatorStore.getElevatorControlSystem().set(elevatorControlSystemResource)
        elevatorControlSystemResource.data?.let { elevatorControlSystem ->
            for (elevator in elevatorControlSystem.elevators) {
                elevatorStore.getElevator(elevator.elevatorNumber).set(Resource(elevatorControlSystemResource.status, elevator, elevatorControlSystemResource.error))
                for (floorButton in elevator.buttons) {
                    elevatorStore.getFloorButton(elevator.elevatorNumber, floorButton.floorNumber).set(Resource(elevatorControlSystemResource.status, floorButton, elevatorControlSystemResource.error))
                }
                for (servicedFloor in elevator.servicedFloors) {
                    elevatorStore.getServicedFloor(elevator.elevatorNumber, servicedFloor.floorNumber).set(Resource(elevatorControlSystemResource.status, servicedFloor, elevatorControlSystemResource.error))
                }
            }
            for (buildingFloor in elevatorControlSystem.floors) {
                elevatorStore.getBuildingFloor(buildingFloor.floorNumber).set(Resource(elevatorControlSystemResource.status, buildingFloor, elevatorControlSystemResource.error))
            }
        }
    }
}