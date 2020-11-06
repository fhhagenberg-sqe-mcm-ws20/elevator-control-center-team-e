package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.DoorState
import at.fhhagenberg.sqe.entity.Elevator
import com.google.inject.Inject
import sqelevator.IElevator
import java.rmi.RemoteException
import java.util.*

class ElevatorServiceImpl @Inject constructor(
        private val elevatorControl: IElevator,
        private val servicedFloorService: ServicedFloorService,
        private val floorButtonService: FloorButtonService
) : ElevatorService {
    @Throws(RemoteException::class)
    override fun getAll(): List<Elevator> {
        val totalNumberOfElevators = elevatorControl.elevatorNum
        val elevators: MutableList<Elevator> = ArrayList(totalNumberOfElevators)
        for (elevatorNumber in 0 until totalNumberOfElevators) {
            get(elevatorNumber)?.let { elevator ->
                elevators.add(elevator)
            }
        }
        return elevators
    }

    @Throws(RemoteException::class)
    override fun get(elevatorNumber: Int): Elevator? {
        return if (elevatorNumber in 0 until elevatorControl.floorNum) {
            Elevator.Builder()
                    .elevatorNumber(elevatorNumber)
                    .acceleration(elevatorControl.getElevatorAccel(elevatorNumber))
                    .buttons(floorButtonService.getAll(elevatorNumber))
                    .capacity(elevatorControl.getElevatorCapacity(elevatorNumber))
                    .committedDirection(Direction.valueOf(elevatorControl.getCommittedDirection(elevatorNumber)))
                    .currentFloor(elevatorControl.getElevatorFloor(elevatorNumber))
                    .currentPosition(elevatorControl.getElevatorPosition(elevatorNumber))
                    .currentSpeed(elevatorControl.getElevatorSpeed(elevatorNumber))
                    .currentWeight(elevatorControl.getElevatorWeight(elevatorNumber))
                    .doorState(DoorState.valueOf(elevatorControl.getElevatorDoorStatus(elevatorNumber)))
                    .servicedFloors(servicedFloorService.getAll(elevatorNumber))
                    .targetFloor(elevatorControl.getTarget(elevatorNumber))
                    .build()
        } else null
    }

    @Throws(RemoteException::class)
    override fun updateCommittedDirection(elevator: Elevator) {
        elevatorControl.setCommittedDirection(elevator.elevatorNumber, elevator.committedDirection.direction)
    }

    @Throws(RemoteException::class)
    override fun updateTargetFloor(elevator: Elevator) {
        elevatorControl.setTarget(elevator.elevatorNumber, elevator.targetFloor)
    }
}