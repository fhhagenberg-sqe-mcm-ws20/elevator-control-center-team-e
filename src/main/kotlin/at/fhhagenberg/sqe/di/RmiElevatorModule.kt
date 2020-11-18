package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.DoorState
import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.Provider
import com.google.inject.Singleton
import sqelevator.IElevator

class RmiElevatorModule : AbstractModule() {
    override fun configure() {
        // TODO: Bind IElevator to RMI IElevator implementation
        // bind(Key.get(IElevator::class.java, RealIElevator::class.java)).to(SOMETHING).`in`(Singleton::class.java)
        bind(Key.get(IElevator::class.java, RealIElevator::class.java)).toInstance(
                object : IElevator {
                    override fun getCommittedDirection(elevatorNumber: Int): Int {
                        return Direction.UNCOMMITTED.direction
                    }

                    override fun getElevatorAccel(elevatorNumber: Int): Int {
                        return 1
                    }

                    override fun getElevatorButton(elevatorNumber: Int, floor: Int): Boolean {
                        return false
                    }

                    override fun getElevatorDoorStatus(elevatorNumber: Int): Int {
                        return DoorState.CLOSED.doorState
                    }

                    override fun getElevatorFloor(elevatorNumber: Int): Int {
                        return 2
                    }

                    override fun getElevatorNum(): Int {
                        return 4
                    }

                    override fun getElevatorPosition(elevatorNumber: Int): Int {
                        return 20
                    }

                    override fun getElevatorSpeed(elevatorNumber: Int): Int {
                        return 0
                    }

                    override fun getElevatorWeight(elevatorNumber: Int): Int {
                        return 250
                    }

                    override fun getElevatorCapacity(elevatorNumber: Int): Int {
                        return 8
                    }

                    override fun getFloorButtonDown(floor: Int): Boolean {
                        return false
                    }

                    override fun getFloorButtonUp(floor: Int): Boolean {
                        return false
                    }

                    override fun getFloorHeight(): Int {
                        return 40
                    }

                    override fun getFloorNum(): Int {
                        return 5
                    }

                    override fun getServicesFloors(elevatorNumber: Int, floor: Int): Boolean {
                        return true
                    }

                    override fun getTarget(elevatorNumber: Int): Int {
                        return 0
                    }

                    override fun setCommittedDirection(elevatorNumber: Int, direction: Int) {
                    }

                    override fun setServicesFloors(elevatorNumber: Int, floor: Int, service: Boolean) {
                    }

                    override fun setTarget(elevatorNumber: Int, target: Int) {
                    }

                    override fun getClockTick(): Long {
                        return 0L
                    }
                }
        )
    }
}