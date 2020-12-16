package sqelevator

import at.fhhagenberg.sqe.di.key.RmiUrl
import com.google.inject.Inject
import java.rmi.Naming

class RmiIElevator @Inject constructor(
        @RmiUrl private val rmiUrl: String
) : ConnectableIElevator {

    private var initialized = false
    private var _controller: IElevator? = null
    private val controller: IElevator
            get() {
                if (!initialized) {
                    initialized = true
                    connect()
                }
                return _controller!!
            }

    @Synchronized
    override fun connect() {
        _controller = CachedElevatorControl(Naming.lookup(rmiUrl) as IElevator)
    }

    @Synchronized
    override fun getCommittedDirection(elevatorNumber: Int) = controller.getCommittedDirection(elevatorNumber)

    @Synchronized
    override fun getElevatorAccel(elevatorNumber: Int) = controller.getElevatorAccel(elevatorNumber)

    @Synchronized
    override fun getElevatorButton(elevatorNumber: Int, floor: Int) = controller.getElevatorButton(elevatorNumber, floor)

    @Synchronized
    override fun getElevatorDoorStatus(elevatorNumber: Int) = controller.getElevatorDoorStatus(elevatorNumber)

    @Synchronized
    override fun getElevatorFloor(elevatorNumber: Int) = controller.getElevatorFloor(elevatorNumber)

    @Synchronized
    override fun getElevatorNum() = controller.elevatorNum

    @Synchronized
    override fun getElevatorPosition(elevatorNumber: Int) = controller.getElevatorPosition(elevatorNumber)

    @Synchronized
    override fun getElevatorSpeed(elevatorNumber: Int) = controller.getElevatorSpeed(elevatorNumber)

    @Synchronized
    override fun getElevatorWeight(elevatorNumber: Int) = controller.getElevatorWeight(elevatorNumber)

    @Synchronized
    override fun getElevatorCapacity(elevatorNumber: Int) = controller.getElevatorCapacity(elevatorNumber)

    @Synchronized
    override fun getFloorButtonDown(floor: Int) = controller.getFloorButtonDown(floor)

    @Synchronized
    override fun getFloorButtonUp(floor: Int) = controller.getFloorButtonUp(floor)

    @Synchronized
    override fun getFloorHeight() = controller.floorHeight

    @Synchronized
    override fun getFloorNum() = controller.floorNum

    @Synchronized
    override fun getServicesFloors(elevatorNumber: Int, floor: Int) = controller.getServicesFloors(elevatorNumber, floor)

    @Synchronized
    override fun getTarget(elevatorNumber: Int) = controller.getTarget(elevatorNumber)

    @Synchronized
    override fun setCommittedDirection(elevatorNumber: Int, direction: Int) = controller.setCommittedDirection(elevatorNumber, direction)

    @Synchronized
    override fun setServicesFloors(elevatorNumber: Int, floor: Int, service: Boolean) = controller.setServicesFloors(elevatorNumber, floor, service)

    @Synchronized
    override fun setTarget(elevatorNumber: Int, target: Int) = controller.setTarget(elevatorNumber, target)

    @Synchronized
    override fun getClockTick() = controller.clockTick
}