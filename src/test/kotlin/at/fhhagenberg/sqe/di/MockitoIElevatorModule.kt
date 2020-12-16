package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.adapter.ElevatorAdapter
import at.fhhagenberg.sqe.adapter.RmiAndDummyElevatorAdapter
import at.fhhagenberg.sqe.store.ElevatorStore
import at.fhhagenberg.sqe.store.ElevatorStoreImpl
import com.google.inject.AbstractModule
import com.google.inject.Singleton
import sqelevator.ConnectableIElevator

class MockitoIElevatorModule : AbstractModule() {
    override fun configure() {
        bind(ConnectableIElevator::class.java).toProvider(MockitoIElevatorProvider::class.java).`in`(Singleton::class.java)
        bind(ElevatorAdapter::class.java).to(RmiAndDummyElevatorAdapter::class.java).`in`(Singleton::class.java)
        bind(ElevatorStore::class.java).to(ElevatorStoreImpl::class.java).`in`(Singleton::class.java)
    }
}