package at.fhhagenberg.sqe.di

import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.Singleton
import sqelevator.IElevator

class MockRmiElevatorModule : AbstractModule() {
    override fun configure() {
        bind(Key.get(IElevator::class.java, RealIElevator::class.java)).toProvider(RealIElevatorMockProvider::class.java).`in`(Singleton::class.java)
    }
}