package at.fhhagenberg.sqe.di.module

import at.fhhagenberg.sqe.adapter.ElevatorAdapter
import at.fhhagenberg.sqe.adapter.RmiAndDummyElevatorAdapter
import at.fhhagenberg.sqe.store.ElevatorStore
import at.fhhagenberg.sqe.store.ElevatorStoreImpl
import at.fhhagenberg.sqe.di.key.RmiUrl
import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.Singleton
import sqelevator.ConnectableIElevator
import sqelevator.RmiIElevator

class RmiIElevatorModule : AbstractModule() {

    override fun configure() {
        bind(Key.get(String::class.java, RmiUrl::class.java)).toInstance("rmi://localhost/ElevatorSim")
        bind(ConnectableIElevator::class.java).to(RmiIElevator::class.java).`in`(Singleton::class.java)
        bind(ElevatorAdapter::class.java).to(RmiAndDummyElevatorAdapter::class.java).`in`(Singleton::class.java)
        bind(ElevatorStore::class.java).to(ElevatorStoreImpl::class.java).`in`(Singleton::class.java)
    }
}