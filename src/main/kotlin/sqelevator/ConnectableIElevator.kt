package sqelevator

import java.rmi.RemoteException

interface ConnectableIElevator : IElevator {
    @Throws(RemoteException::class)
    fun connect()
}