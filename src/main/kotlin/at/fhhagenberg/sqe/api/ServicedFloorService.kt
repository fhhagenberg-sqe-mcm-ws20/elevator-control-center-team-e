package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.entity.ServicedFloor
import java.rmi.RemoteException

interface ServicedFloorService {
    @Throws(RemoteException::class)
    fun getAll(elevatorNumber: Int): List<ServicedFloor>

    @Throws(RemoteException::class)
    fun get(elevatorNumber: Int, floorNumber: Int): ServicedFloor?

    @Throws(RemoteException::class)
    fun updateServicedFloor(servicedFloor: ServicedFloor, isServiced: Boolean)
}