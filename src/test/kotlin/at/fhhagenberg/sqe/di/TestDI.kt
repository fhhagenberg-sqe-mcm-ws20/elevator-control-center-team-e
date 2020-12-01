package at.fhhagenberg.sqe.di

import com.google.inject.Guice
import com.google.inject.Injector

object TestDI {
    @JvmStatic
    fun createInjector(): Injector =
        Guice.createInjector(
                InstantAppExecutorsModule(),
                ElevatorServicesModule(),
                MockRmiElevatorModule(),
                ElevatorRepositoryModule(),
                ViewModelsModule(),
                JavaFxModule()
        )
}