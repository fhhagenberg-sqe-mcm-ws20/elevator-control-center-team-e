package at.fhhagenberg.sqe.di.module

import at.fhhagenberg.sqe.di.key.PollingInterval
import at.fhhagenberg.sqe.task.Polling
import at.fhhagenberg.sqe.task.RealPolling
import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.Singleton

class RealPollingModule : AbstractModule() {
    override fun configure() {
        bind(Key.get(Long::class.java, PollingInterval::class.java)).toInstance(100L)
        bind(Polling::class.java).to(RealPolling::class.java).`in`(Singleton::class.java)
    }
}