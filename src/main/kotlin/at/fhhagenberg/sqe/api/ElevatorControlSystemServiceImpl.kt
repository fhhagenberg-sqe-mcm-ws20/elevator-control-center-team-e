package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import com.google.inject.Inject
import sqelevator.ConnectableIElevator
import java.rmi.RemoteException

class ElevatorControlSystemServiceImpl @Inject constructor(
        private val elevatorControl: ConnectableIElevator,
        private val elevatorService: ElevatorService,
        private val buildingFloorService: BuildingFloorService
) : ElevatorControlSystemService {
    @Throws(RemoteException::class)
    override fun get(): ElevatorControlSystem {
        val clockTick = elevatorControl.clockTick
        val floorHeight = elevatorControl.floorHeight
        val floors = buildingFloorService.getAll()
        val elevators = elevatorService.getAll()
        return ElevatorControlSystem(clockTick, floorHeight, floors, elevators)
    }
}