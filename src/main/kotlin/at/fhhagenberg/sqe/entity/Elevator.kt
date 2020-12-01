package at.fhhagenberg.sqe.entity

data class Elevator(
        val elevatorNumber: Int,
        var committedDirection: Direction,
        val acceleration: Int,
        val buttons: List<FloorButton>,
        val doorState: DoorState,
        val currentFloor: Int,
        val currentPosition: Int,
        val currentWeight: Int,
        val currentSpeed: Int,
        val capacity: Int,
        var targetFloor: Int,
        val servicedFloors: List<ServicedFloor>
) {
    fun getButton(floorNumber: Int): FloorButton? = buttons.firstOrNull { it.floorNumber == floorNumber }
    fun getServicedFloor(floorNumber: Int): ServicedFloor? = servicedFloors.firstOrNull { it.floorNumber == floorNumber }
    fun evaluateDirection(targetFloor: Int): Direction {
        return when {
            targetFloor > currentFloor -> Direction.UP
            targetFloor < currentFloor -> Direction.DOWN
            else -> Direction.UNCOMMITTED
        }
    }

    class Builder {
        private var elevatorNumber = 0
        private var committedDirection: Direction = Direction.UNKNOWN
        private var acceleration = 0
        private var buttons: List<FloorButton> = emptyList()
        private var doorState: DoorState = DoorState.UNKNOWN
        private var currentFloor = 0
        private var currentPosition = 0
        private var currentWeight = 0
        private var currentSpeed = 0
        private var capacity = 0
        private var targetFloor = 0
        private var servicedFloors: List<ServicedFloor> = emptyList()

        fun elevatorNumber(elevatorNumber: Int) = apply {
            this.elevatorNumber = elevatorNumber
        }

        fun committedDirection(committedDirection: Direction) = apply {
            this.committedDirection = committedDirection
        }

        fun acceleration(acceleration: Int) = apply {
            this.acceleration = acceleration
        }

        fun buttons(buttons: List<FloorButton>) = apply {
            this.buttons = buttons
        }

        fun doorState(doorState: DoorState) = apply {
            this.doorState = doorState
        }

        fun currentFloor(currentFloor: Int) = apply {
            this.currentFloor = currentFloor
        }

        fun currentPosition(currentPosition: Int) = apply {
            this.currentPosition = currentPosition
        }

        fun currentWeight(currentWeight: Int) = apply {
            this.currentWeight = currentWeight
        }

        fun currentSpeed(currentSpeed: Int) = apply {
            this.currentSpeed = currentSpeed
        }

        fun capacity(capacity: Int) = apply {
            this.capacity = capacity
        }

        fun targetFloor(targetFloor: Int) = apply {
            this.targetFloor = targetFloor
        }

        fun servicedFloors(servicedFloors: List<ServicedFloor>) = apply {
            this.servicedFloors = servicedFloors
        }

        fun build(): Elevator {
            return Elevator(
                    elevatorNumber,
                    committedDirection,
                    acceleration,
                    buttons,
                    doorState,
                    currentFloor,
                    currentPosition,
                    currentWeight,
                    currentSpeed,
                    capacity,
                    targetFloor,
                    servicedFloors)
        }
    }
}