package sqelevator

import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import com.google.inject.Inject

class DummyIElevator @Inject constructor(
        private val elevatorControlSystem: ElevatorControlSystem
) : ConnectableIElevator {

    override fun connect() { }

    override fun getCommittedDirection(elevatorNumber: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.committedDirection.direction

    override fun getElevatorAccel(elevatorNumber: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.acceleration

    override fun getElevatorButton(elevatorNumber: Int, floor: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.getButton(floor)!!.isActive

    override fun getElevatorDoorStatus(elevatorNumber: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.doorState.doorState

    override fun getElevatorFloor(elevatorNumber: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.currentFloor

    override fun getElevatorNum() = elevatorControlSystem.numberOfElevators

    override fun getElevatorPosition(elevatorNumber: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.currentPosition

    override fun getElevatorSpeed(elevatorNumber: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.currentSpeed

    override fun getElevatorWeight(elevatorNumber: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.currentWeight

    override fun getElevatorCapacity(elevatorNumber: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.capacity

    override fun getFloorButtonDown(floor: Int) =
            elevatorControlSystem.getFloor(floor)!!.isDownActive

    override fun getFloorButtonUp(floor: Int) =
            elevatorControlSystem.getFloor(floor)!!.isUpActive

    override fun getFloorHeight() = elevatorControlSystem.floorHeight

    override fun getFloorNum() = elevatorControlSystem.numberOfFloors

    override fun getServicesFloors(elevatorNumber: Int, floor: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.getServicedFloor(floor)!!.isServiced

    override fun getTarget(elevatorNumber: Int) =
            elevatorControlSystem.getElevator(elevatorNumber)!!.targetFloor

    override fun setCommittedDirection(elevatorNumber: Int, direction: Int) {
        elevatorControlSystem.getElevator(elevatorNumber)!!.committedDirection = Direction.valueOf(direction)
    }

    override fun setServicesFloors(elevatorNumber: Int, floor: Int, service: Boolean) {
        elevatorControlSystem.getElevator(elevatorNumber)!!.getServicedFloor(floor)!!.isServiced = service
    }

    override fun setTarget(elevatorNumber: Int, target: Int) {
        elevatorControlSystem.getElevator(elevatorNumber)!!.targetFloor = target
    }

    override fun getClockTick() = elevatorControlSystem.clockTick
}