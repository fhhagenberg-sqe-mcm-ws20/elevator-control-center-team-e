package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.entity.FloorButton
import com.google.inject.Inject
import sqelevator.ConnectableIElevator
import java.rmi.RemoteException
import java.util.*

class FloorButtonServiceImpl @Inject constructor(
        private val elevatorControl: ConnectableIElevator
) : FloorButtonService {
    @Throws(RemoteException::class)
    override fun getAll(elevatorNumber: Int): List<FloorButton> {
        val totalNumberOfElevators = elevatorControl.elevatorNum
        val totalNumberOfFloors = elevatorControl.floorNum
        val floorButtons: MutableList<FloorButton> = ArrayList(totalNumberOfFloors.coerceAtLeast(0))
        if (elevatorNumber in 0 until totalNumberOfElevators) {
            for (floorNumber in 0 until totalNumberOfFloors) {
                floorButtons.add(get(elevatorNumber, floorNumber)!!)
            }
        }
        return floorButtons
    }

    @Throws(RemoteException::class)
    override fun get(elevatorNumber: Int, floorNumber: Int): FloorButton? {
        val totalNumberOfElevators = elevatorControl.elevatorNum
        val totalNumberOfFloors = elevatorControl.floorNum
        return if (elevatorNumber in 0 until totalNumberOfElevators && floorNumber in 0 until totalNumberOfFloors) {
            val isActive = elevatorControl.getElevatorButton(elevatorNumber, floorNumber)
            FloorButton(elevatorNumber, floorNumber, isActive)
        } else null
    }
}