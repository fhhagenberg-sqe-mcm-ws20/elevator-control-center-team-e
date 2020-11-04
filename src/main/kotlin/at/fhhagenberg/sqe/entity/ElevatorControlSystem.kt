package at.fhhagenberg.sqe.entity

data class ElevatorControlSystem(
        val clockTick: Long,
        val floorHeight: Int,
        val floors: List<BuildingFloor>,
        val elevators: List<Elevator>
) {
    val numberOfFloors get() = floors.size
    val numberOfElevators get() = elevators.size

    fun getFloor(floorNumber: Int): BuildingFloor? = floors.firstOrNull { it.floorNumber == floorNumber }
    fun getElevator(elevatorNumber: Int): Elevator? = elevators.firstOrNull { it.elevatorNumber == elevatorNumber }
}