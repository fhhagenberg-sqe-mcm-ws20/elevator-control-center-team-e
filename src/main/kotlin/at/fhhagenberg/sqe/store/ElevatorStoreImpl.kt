package at.fhhagenberg.sqe.store

import at.fhhagenberg.sqe.entity.*
import at.fhhagenberg.sqe.model.Resource
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty

class ElevatorStoreImpl : ElevatorStore {

    private val elevatorControlSystem = SimpleObjectProperty<Resource<ElevatorControlSystem>>(Resource.loading(null))
    private val elevators = mutableMapOf<Int, SimpleObjectProperty<Resource<Elevator>>>()
    private val floorButtons = mutableMapOf<Pair<Int, Int>, SimpleObjectProperty<Resource<FloorButton>>>()
    private val servicedFloors = mutableMapOf<Pair<Int, Int>, SimpleObjectProperty<Resource<ServicedFloor>>>()
    private val buildingFloors = mutableMapOf<Int, SimpleObjectProperty<Resource<BuildingFloor>>>()

    @Synchronized
    override fun getElevatorControlSystem() = elevatorControlSystem

    @Synchronized
    override fun getElevator(elevatorNumber: Int) = ensureInitialized(elevatorNumber, elevators)

    @Synchronized
    override fun getFloorButton(elevatorNumber: Int, floorNumber: Int) = ensureInitialized(elevatorNumber to floorNumber, floorButtons)

    @Synchronized
    override fun getServicedFloor(elevatorNumber: Int, floorNumber: Int) = ensureInitialized(elevatorNumber to floorNumber, servicedFloors)

    @Synchronized
    override fun getBuildingFloor(floorNumber: Int) = ensureInitialized(floorNumber, buildingFloors)

    private fun <K, V> ensureInitialized(key: K, map: MutableMap<K, SimpleObjectProperty<Resource<V>>>): ObjectProperty<Resource<V>> {
        if (!map.containsKey(key)) {
            map[key] = SimpleObjectProperty<Resource<V>>(Resource.loading(null))
        }
        return map.getValue(key)
    }
}