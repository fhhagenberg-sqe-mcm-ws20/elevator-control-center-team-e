package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.repository.ElevatorRepository
import at.fhhagenberg.sqe.repository.ElevatorStore
import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.Singleton

class ElevatorRepositoryModule : AbstractModule() {
    override fun configure() {
        bind(ElevatorRepository::class.java).`in`(Singleton::class.java)
        bind(ElevatorStore::class.java).`in`(Singleton::class.java)
        bind(Key.get(Long::class.java, PollingInterval::class.java)).toInstance(100L)
    }
}