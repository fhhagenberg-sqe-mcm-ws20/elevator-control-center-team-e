package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.FloorButton
import com.google.inject.Inject
import sqelevator.IElevator
import java.rmi.RemoteException
import java.util.*

class FloorButtonServiceImpl @Inject constructor(
        private val elevatorControl: IElevator
) : FloorButtonService {
    @Throws(RemoteException::class)
    override fun getAll(elevatorNumber: Int): List<FloorButton> {
        val totalNumberOfElevators = elevatorControl.elevatorNum
        var totalNumberOfFloors = elevatorControl.floorNum
        if (totalNumberOfFloors < 0) {
            totalNumberOfFloors = 0
        }
        val floorButtons: MutableList<FloorButton> = ArrayList(totalNumberOfFloors)
        if (elevatorNumber in 0 until totalNumberOfElevators) {
            for (floorNumber in 0 until totalNumberOfFloors) {
                get(elevatorNumber, floorNumber)?.let { button ->
                    floorButtons.add(button)
                }
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