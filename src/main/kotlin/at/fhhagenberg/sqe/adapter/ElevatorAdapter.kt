package at.fhhagenberg.sqe.adapter

import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.entity.ServicedFloor
import at.fhhagenberg.sqe.model.Resource

interface ElevatorAdapter {
    fun getElevatorControlSystem(): Resource<ElevatorControlSystem>
    fun updateServicedFloor(servicedFloor: ServicedFloor, isServiced: Boolean): Resource<Boolean>
    fun updateTargetFloor(elevator: Elevator, targetFloor: Int): Resource<Boolean>
    fun reconnect(): Resource<Boolean>
}