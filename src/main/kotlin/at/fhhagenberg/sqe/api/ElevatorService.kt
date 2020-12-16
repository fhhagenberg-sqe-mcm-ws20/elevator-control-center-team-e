package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.Elevator
import java.rmi.RemoteException

interface ElevatorService {
    @Throws(RemoteException::class)
    fun getAll(): List<Elevator>

    @Throws(RemoteException::class)
    fun get(elevatorNumber: Int): Elevator?

    @Throws(RemoteException::class)
    fun updateTargetFloor(elevator: Elevator, targetFloor: Int)
}