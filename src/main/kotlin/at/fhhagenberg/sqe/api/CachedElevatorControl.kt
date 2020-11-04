package at.fhhagenberg.sqe.api

import sqelevator.IElevator
import at.fhhagenberg.sqe.di.RealIElevator
import com.google.inject.Inject

class CachedElevatorControl
@Inject
constructor (
        @RealIElevator private val elevatorControl: IElevator
) : IElevator {

    private var elevatorNum: Int? = null
    private val elevatorCapacity = mutableMapOf<Int, Int>()
    private var floorHeight: Int? = null
    private var floorNum: Int? = null

    override fun getCommittedDirection(elevatorNumber: Int) = elevatorControl.getCommittedDirection(elevatorNumber)

    override fun getElevatorAccel(elevatorNumber: Int) = elevatorControl.getElevatorAccel(elevatorNumber)

    override fun getElevatorButton(elevatorNumber: Int, floor: Int) = elevatorControl.getElevatorButton(elevatorNumber, floor)

    override fun getElevatorDoorStatus(elevatorNumber: Int) = elevatorControl.getElevatorDoorStatus(elevatorNumber)

    override fun getElevatorFloor(elevatorNumber: Int) = elevatorControl.getElevatorFloor(elevatorNumber)

    override fun getElevatorNum(): Int {
        if (elevatorNum == null) {
            elevatorNum = elevatorControl.elevatorNum
        }
        return elevatorNum!!
    }

    override fun getElevatorPosition(elevatorNumber: Int) = elevatorControl.getElevatorPosition(elevatorNumber)

    override fun getElevatorSpeed(elevatorNumber: Int) = elevatorControl.getElevatorSpeed(elevatorNumber)

    override fun getElevatorWeight(elevatorNumber: Int) = elevatorControl.getElevatorWeight(elevatorNumber)

    override fun getElevatorCapacity(elevatorNumber: Int): Int {
        if (!elevatorCapacity.containsKey(elevatorNumber)) {
            elevatorCapacity[elevatorNumber] = elevatorControl.getElevatorCapacity(elevatorNumber)
        }
        return elevatorCapacity.getValue(elevatorNumber)
    }

    override fun getFloorButtonDown(floor: Int) = elevatorControl.getFloorButtonDown(floor)

    override fun getFloorButtonUp(floor: Int) = elevatorControl.getFloorButtonUp(floor)

    override fun getFloorHeight(): Int {
        if (floorHeight == null) {
            floorHeight = elevatorControl.floorHeight
        }
        return floorHeight!!
    }

    override fun getFloorNum(): Int {
        if (floorNum == null) {
            floorNum = elevatorControl.floorNum
        }
        return floorNum!!
    }

    override fun getServicesFloors(elevatorNumber: Int, floor: Int) = elevatorControl.getServicesFloors(elevatorNumber, floor)

    override fun getTarget(elevatorNumber: Int) = elevatorControl.getTarget(elevatorNumber)

    override fun setCommittedDirection(elevatorNumber: Int, direction: Int) = elevatorControl.setCommittedDirection(elevatorNumber, direction)

    override fun setServicesFloors(elevatorNumber: Int, floor: Int, service: Boolean) = elevatorControl.setServicesFloors(elevatorNumber, floor, service)

    override fun setTarget(elevatorNumber: Int, target: Int) = elevatorControl.setTarget(elevatorNumber, target)

    override fun getClockTick() = elevatorControl.clockTick
}