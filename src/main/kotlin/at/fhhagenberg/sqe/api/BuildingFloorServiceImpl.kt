package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.BuildingFloor
import com.google.inject.Inject
import sqelevator.IElevator
import java.rmi.RemoteException
import java.util.*

class BuildingFloorServiceImpl @Inject constructor(
        private val elevatorControl: IElevator
) : BuildingFloorService {
    @Throws(RemoteException::class)
    override fun getAll(): List<BuildingFloor> {
        val totalNumberOfFloors = elevatorControl.floorNum
        val buildingFloors: MutableList<BuildingFloor> = ArrayList(totalNumberOfFloors.coerceAtLeast(0))
        for (floorNumber in 0 until totalNumberOfFloors) {
            get(floorNumber)?.let { floor ->
                buildingFloors.add(floor)
            }
        }
        return buildingFloors
    }

    @Throws(RemoteException::class)
    override fun get(floorNumber: Int): BuildingFloor? {
        val totalNumberOfFloors = elevatorControl.floorNum
        return if (floorNumber in 0 until totalNumberOfFloors) {
            val isUpActive = elevatorControl.getFloorButtonUp(floorNumber)
            val isDownActive = elevatorControl.getFloorButtonDown(floorNumber)
            BuildingFloor(floorNumber, isDownActive, isUpActive)
        } else null
    }
}