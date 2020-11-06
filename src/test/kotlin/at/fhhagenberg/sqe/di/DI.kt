package at.fhhagenberg.sqe.di

import com.google.inject.Guice

object DI {
    @JvmStatic
    fun createInjector() =
        Guice.createInjector(
                ElevatorServicesModule(),
                MockRmiElevatorModule()
        )
}