package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.di.key.PollingInterval
import at.fhhagenberg.sqe.task.MockPolling
import at.fhhagenberg.sqe.task.Polling
import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.Singleton

class MockPollingModule : AbstractModule() {
    override fun configure() {
        bind(Key.get(Long::class.java, PollingInterval::class.java)).toInstance(100L)
        bind(Polling::class.java).to(MockPolling::class.java).`in`(Singleton::class.java)
    }
}