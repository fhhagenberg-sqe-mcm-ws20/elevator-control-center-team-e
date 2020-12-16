package at.fhhagenberg.sqe.store

import at.fhhagenberg.sqe.entity.*
import at.fhhagenberg.sqe.model.Resource
import javafx.beans.property.ObjectProperty

interface ElevatorStore {
    fun getElevatorControlSystem(): ObjectProperty<Resource<ElevatorControlSystem>>
    fun getElevator(elevatorNumber: Int): ObjectProperty<Resource<Elevator>>
    fun getFloorButton(elevatorNumber: Int, floorNumber: Int): ObjectProperty<Resource<FloorButton>>
    fun getServicedFloor(elevatorNumber: Int, floorNumber: Int): ObjectProperty<Resource<ServicedFloor>>
    fun getBuildingFloor(floorNumber: Int): ObjectProperty<Resource<BuildingFloor>>
}