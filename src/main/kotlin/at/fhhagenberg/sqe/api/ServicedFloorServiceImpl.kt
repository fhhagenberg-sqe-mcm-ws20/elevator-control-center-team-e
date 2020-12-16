package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.entity.ServicedFloor
import com.google.inject.Inject
import sqelevator.ConnectableIElevator
import java.rmi.RemoteException
import java.util.*

class ServicedFloorServiceImpl @Inject constructor(
        private val elevatorControl: ConnectableIElevator
) : ServicedFloorService {
    @Throws(RemoteException::class)
    override fun getAll(elevatorNumber: Int): List<ServicedFloor> {
        val totalNumberOfElevators = elevatorControl.elevatorNum
        val totalNumberOfFloors = elevatorControl.floorNum
        val servicedFloors: MutableList<ServicedFloor> = ArrayList(totalNumberOfFloors.coerceAtLeast(0))
        if (elevatorNumber in 0 until totalNumberOfElevators) {
            for (floorNumber in 0 until totalNumberOfFloors) {
                servicedFloors.add(get(elevatorNumber, floorNumber)!!)
            }
        }
        return servicedFloors
    }

    @Throws(RemoteException::class)
    override fun get(elevatorNumber: Int, floorNumber: Int): ServicedFloor? {
        val totalNumberOfElevators = elevatorControl.elevatorNum
        val totalNumberOfFloors = elevatorControl.floorNum
        if (elevatorNumber in 0 until totalNumberOfElevators && floorNumber in 0 until totalNumberOfFloors) {
            val isElevatorButtonServiced = elevatorControl.getServicesFloors(elevatorNumber, floorNumber)
            return ServicedFloor(elevatorNumber, floorNumber, isElevatorButtonServiced)
        }
        return null
    }

    @Throws(RemoteException::class)
    override fun updateServicedFloor(servicedFloor: ServicedFloor, isServiced: Boolean) {
        elevatorControl.setServicesFloors(servicedFloor.elevatorNumber, servicedFloor.floorNumber, isServiced)
    }
}