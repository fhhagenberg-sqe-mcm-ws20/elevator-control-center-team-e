package at.fhhagenberg.sqe.di

import com.google.inject.Guice
import com.google.inject.Injector

object DI {
    @JvmStatic
    val injector: Injector get() =
        Guice.createInjector(
                ElevatorServicesModule(),
                MockRmiElevatorModule()
        )
}