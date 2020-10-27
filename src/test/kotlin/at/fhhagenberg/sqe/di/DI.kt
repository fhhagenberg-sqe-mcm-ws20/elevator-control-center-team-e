package at.fhhagenberg.sqe.di

import com.google.inject.Guice
import com.google.inject.Injector

object DI {
    private val injector: Injector
        get() = Guice.createInjector(
                ElevatorServicesModule(),
                MockRmiElevatorModule()
        )

    @JvmStatic
    operator fun <T> get(clazz: Class<T>?): T {
        return injector.getInstance(clazz)
    }
}