package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.AppExecutors
import at.fhhagenberg.sqe.util.InstantAppExecutors
import com.google.inject.AbstractModule
import com.google.inject.Provider
import com.google.inject.Singleton
import javafx.application.Platform
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class InstantAppExecutorsModule : AbstractModule() {
    override fun configure() {
        bind(AppExecutors::class.java).to(InstantAppExecutors::class.java).`in`(Singleton::class.java)
    }
}