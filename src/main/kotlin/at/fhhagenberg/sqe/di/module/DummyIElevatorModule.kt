package at.fhhagenberg.sqe.di.module

import at.fhhagenberg.sqe.adapter.ElevatorAdapter
import at.fhhagenberg.sqe.adapter.RmiAndDummyElevatorAdapter
import at.fhhagenberg.sqe.store.ElevatorStore
import at.fhhagenberg.sqe.store.ElevatorStoreImpl
import at.fhhagenberg.sqe.di.provider.ElevatorControlSystemProvider
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import com.google.inject.AbstractModule
import com.google.inject.Singleton
import sqelevator.ConnectableIElevator
import sqelevator.DummyIElevator

class DummyIElevatorModule : AbstractModule() {

    override fun configure() {
        bind(ElevatorControlSystem::class.java).toProvider(ElevatorControlSystemProvider::class.java).`in`(Singleton::class.java)
        bind(ConnectableIElevator::class.java).to(DummyIElevator::class.java).`in`(Singleton::class.java)
        bind(ElevatorAdapter::class.java).to(RmiAndDummyElevatorAdapter::class.java).`in`(Singleton::class.java)
        bind(ElevatorStore::class.java).to(ElevatorStoreImpl::class.java).`in`(Singleton::class.java)
    }
}