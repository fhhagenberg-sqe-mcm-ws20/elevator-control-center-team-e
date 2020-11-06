package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.api.*
import com.google.inject.AbstractModule
import com.google.inject.Singleton
import sqelevator.CachedElevatorControl
import sqelevator.IElevator

class ElevatorServicesModule : AbstractModule() {
    override fun configure() {
        bind(BuildingFloorService::class.java).to(BuildingFloorServiceImpl::class.java)
        bind(ElevatorControlSystemService::class.java).to(ElevatorControlSystemServiceImpl::class.java)
        bind(ElevatorService::class.java).to(ElevatorServiceImpl::class.java)
        bind(FloorButtonService::class.java).to(FloorButtonServiceImpl::class.java)
        bind(ServicedFloorService::class.java).to(ServicedFloorServiceImpl::class.java)
        bind(IElevator::class.java).to(CachedElevatorControl::class.java).`in`(Singleton::class.java)
    }
}