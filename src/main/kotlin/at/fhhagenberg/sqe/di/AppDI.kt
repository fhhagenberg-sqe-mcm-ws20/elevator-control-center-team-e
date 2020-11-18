package at.fhhagenberg.sqe.di

import com.google.inject.Guice
import com.google.inject.Injector

object AppDI {
    @JvmStatic
    fun createInjector(): Injector =
            Guice.createInjector(
                    JavaFxModule(),
                    JavaFxAppExecutorsModule(),
                    ViewModelsModule(),
                    ElevatorRepositoryModule(),
                    ElevatorServicesModule(),
                    RmiElevatorModule()
            )
}