package at.fhhagenberg.sqe.adapter

import at.fhhagenberg.sqe.api.ElevatorControlSystemService
import at.fhhagenberg.sqe.api.ElevatorService
import at.fhhagenberg.sqe.api.ServicedFloorService
import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.entity.ServicedFloor
import at.fhhagenberg.sqe.model.ErrorCode
import at.fhhagenberg.sqe.model.Error
import at.fhhagenberg.sqe.model.Resource
import com.google.inject.Inject
import sqelevator.ConnectableIElevator

class RmiAndDummyElevatorAdapter @Inject constructor(
        elevatorControlSystemService: ElevatorControlSystemService,
        private val elevatorService: ElevatorService,
        private val servicedFloorService: ServicedFloorService,
        elevatorControl: ConnectableIElevator
) : BaseElevatorAdapter(elevatorControlSystemService, elevatorControl) {

    private var lastElevatorControlSystem = Resource.loading<ElevatorControlSystem>(null)

    @Synchronized
    override fun getElevatorControlSystem(): Resource<ElevatorControlSystem> {
        return try {
            val data = fetchElevatorControlSystemConsideringClockTick()
            if (data != null) {
                lastElevatorControlSystem = Resource.success(data)
            }
            lastElevatorControlSystem
        } catch (exception: Exception) {
            Resource.error(Error(exception, ErrorCode.CONNECTION_ERROR))
        }
    }

    @Synchronized
    override fun updateServicedFloor(servicedFloor: ServicedFloor, isServiced: Boolean): Resource<Boolean> {
        return try {
            servicedFloorService.updateServicedFloor(servicedFloor, isServiced)
            Resource.success(true)
        } catch (exception: Exception) {
            Resource.error(Error(exception, ErrorCode.CONNECTION_ERROR))
        }
    }

    @Synchronized
    override fun updateTargetFloor(elevator: Elevator, targetFloor: Int): Resource<Boolean> {
        return try {
            elevatorService.updateTargetFloor(elevator, targetFloor)
            Resource.success(true)
        } catch (exception: Exception) {
            Resource.error(Error(exception, ErrorCode.CONNECTION_ERROR))
        }
    }

    @Synchronized
    override fun reconnect(): Resource<Boolean> {
        return try {
            elevatorControl.connect()
            Resource.success(true)
        } catch (exception: Exception) {
            Resource.error(Error(exception, ErrorCode.CONNECTION_ERROR))
        }
    }
}