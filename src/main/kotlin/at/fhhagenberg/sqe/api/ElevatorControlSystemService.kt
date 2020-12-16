package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import java.rmi.RemoteException

interface ElevatorControlSystemService {
    @Throws(RemoteException::class)
    fun get(): ElevatorControlSystem
}