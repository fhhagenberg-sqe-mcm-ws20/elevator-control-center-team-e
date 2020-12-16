package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.di.module.*
import com.google.inject.Guice
import com.google.inject.Injector

object AppDI {
    @JvmStatic
    fun createInjector(): Injector =
            Guice.createInjector(
                    JavaFxModule(),
                    JavaFxAppExecutorsModule(),
                    ViewModelsModule(),
                    ServicesModule(),
                    RmiIElevatorModule(),
                    RealPollingModule()
            )
}