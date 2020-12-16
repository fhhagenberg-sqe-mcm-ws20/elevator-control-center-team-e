package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.entity.BuildingFloor
import java.rmi.RemoteException

interface BuildingFloorService {
    @Throws(RemoteException::class)
    fun getAll(): List<BuildingFloor>

    @Throws(RemoteException::class)
    fun get(floorNumber: Int): BuildingFloor?
}