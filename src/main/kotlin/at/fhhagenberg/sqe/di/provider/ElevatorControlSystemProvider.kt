package at.fhhagenberg.sqe.di.provider

import at.fhhagenberg.sqe.entity.*
import com.google.inject.Provider
import java.util.*

class ElevatorControlSystemProvider : Provider<ElevatorControlSystem> {

    private val totalNumberOfFloors = 6
    private val totalNumberOfElevators = 4
    private val feetPerFloor = 10
    private val capacityPerElevator = 8

    override fun get() = ElevatorControlSystem(
            100L,
            feetPerFloor,
            createMockBuildingFloors(),
            createMockElevators()
    )

    private fun createMockBuildingFloors(): List<BuildingFloor> {
        val result: MutableList<BuildingFloor> = ArrayList(totalNumberOfFloors)
        for (floorNumber in 0 until totalNumberOfFloors) {
            result.add(BuildingFloor(
                    floorNumber,
                    isDownActive = floorNumber == 2,
                    isUpActive = false))
        }
        return result
    }

    private fun createMockElevators(): List<Elevator> {
        val result: MutableList<Elevator> = ArrayList(totalNumberOfElevators)
        for (elevatorNumber in 0 until totalNumberOfElevators) {
            result.add(Elevator.Builder()
                    .elevatorNumber(elevatorNumber)
                    .doorState(DoorState.CLOSED)
                    .acceleration(1)
                    .buttons(createMockButtons(elevatorNumber))
                    .capacity(capacityPerElevator)
                    .committedDirection(if (elevatorNumber == 0) Direction.DOWN else if (elevatorNumber == 1) Direction.UP else Direction.UNCOMMITTED)
                    .currentFloor(if (elevatorNumber == 0) 5 else if (elevatorNumber == 1) 3 else 0)
                    .currentPosition(if (elevatorNumber == 0) 50 else if (elevatorNumber == 1) 30 else 0)
                    .currentSpeed(2)
                    .currentWeight(170)
                    .targetFloor(0)
                    .servicedFloors(createMockServicedFloors(elevatorNumber))
                    .build()
            )
        }
        return result
    }

    private fun createMockButtons(elevatorNumber: Int): List<FloorButton> {
        val result: MutableList<FloorButton> = ArrayList(totalNumberOfFloors)
        for (floorNumber in 0 until totalNumberOfFloors) {
            result.add(FloorButton(
                    elevatorNumber,
                    floorNumber,
                    (elevatorNumber == 1 || elevatorNumber == 3) && floorNumber == 1
            ))
        }
        return result
    }

    private fun createMockServicedFloors(elevatorNumber: Int): List<ServicedFloor> {
        val result: MutableList<ServicedFloor> = ArrayList(totalNumberOfFloors)
        for (floorNumber in 0 until totalNumberOfFloors) {
            result.add(ServicedFloor(
                    elevatorNumber,
                    floorNumber,
                    !(elevatorNumber == 1 && floorNumber == 2)
            ))
        }
        return result
    }
}