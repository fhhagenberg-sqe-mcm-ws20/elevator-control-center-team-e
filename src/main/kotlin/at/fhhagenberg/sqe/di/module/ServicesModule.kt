package at.fhhagenberg.sqe.di.module

import at.fhhagenberg.sqe.api.*
import com.google.inject.AbstractModule

class ServicesModule : AbstractModule() {
    override fun configure() {
        bind(BuildingFloorService::class.java).to(BuildingFloorServiceImpl::class.java)
        bind(ElevatorControlSystemService::class.java).to(ElevatorControlSystemServiceImpl::class.java)
        bind(ElevatorService::class.java).to(ElevatorServiceImpl::class.java)
        bind(FloorButtonService::class.java).to(FloorButtonServiceImpl::class.java)
        bind(ServicedFloorService::class.java).to(ServicedFloorServiceImpl::class.java)
    }
}