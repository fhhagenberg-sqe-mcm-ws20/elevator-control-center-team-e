package at.fhhagenberg.sqe.repository;

import at.fhhagenberg.sqe.api.ElevatorControlSystemService
import at.fhhagenberg.sqe.api.ElevatorService
import at.fhhagenberg.sqe.api.ServicedFloorService
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.entity.ServicedFloor
import at.fhhagenberg.sqe.model.Resource
import com.google.inject.Inject
import sqelevator.IElevator

class ElevatorRepository @Inject constructor(
        private val elevatorControlSystemService: ElevatorControlSystemService,
        private val elevatorService: ElevatorService,
        private val servicedFloorService: ServicedFloorService,
        private val elevatorControl: IElevator
) {
    private var lastElevatorControlSystem = Resource.loading<ElevatorControlSystem>(null)

    fun getElevatorControlSystem(): Resource<ElevatorControlSystem> {
        return try {
            val clockTick1 = elevatorControl.clockTick
            val data = elevatorControlSystemService.get()
            val clockTick2 = elevatorControl.clockTick
            if (clockTick1 == clockTick2) {
                lastElevatorControlSystem = Resource.success(data)
            }
            lastElevatorControlSystem
        } catch (exception: Exception) {
            Resource.error(exception)
        }
    }

    fun updateCommittedDirection(elevator: Elevator?): Resource<Boolean> {
        return try {
            elevatorService.updateCommittedDirection(elevator!!)
            Resource.success(true)
        } catch (exception: Exception) {
            Resource.error(exception)
        }
    }

    fun updateServicedFloor(servicedFloor: ServicedFloor?): Resource<Boolean> {
        return try {
            servicedFloorService.updateServicedFloor(servicedFloor!!)
            Resource.success(true)
        } catch (exception: Exception) {
            Resource.error(exception)
        }
    }

    fun updateTargetFloor(elevator: Elevator?): Resource<Boolean> {
        return try {
            elevatorService.updateTargetFloor(elevator!!)
            Resource.success(true)
        } catch (exception: Exception) {
            Resource.error(exception)
        }
    }
}