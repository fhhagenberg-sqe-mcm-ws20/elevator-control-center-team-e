package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.FloorButton
import java.rmi.RemoteException

interface FloorButtonService {
    @Throws(RemoteException::class)
    fun getAll(elevatorNumber: Int): List<FloorButton>

    @Throws(RemoteException::class)
    fun get(elevatorNumber: Int, floorNumber: Int): FloorButton?
}