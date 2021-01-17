package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.App
import at.fhhagenberg.sqe.di.module.DummyIElevatorModule
import at.fhhagenberg.sqe.di.module.ServicesModule
import at.fhhagenberg.sqe.di.module.JavaFxModule
import at.fhhagenberg.sqe.di.module.ViewModelsModule
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import com.google.inject.Guice
import com.google.inject.Injector

object TestDI {
    @JvmStatic
    fun createMockInjector(): Injector =
            Guice.createInjector(
                    InstantAppExecutorsModule(),
                    ServicesModule(),
                    MockitoIElevatorModule(),
                    ViewModelsModule(),
                    JavaFxModule(),
                    MockPollingModule()
            )

    @JvmStatic
    fun createDummyInjector(): Injector =
        Guice.createInjector(
            InstantAppExecutorsModule(),
            ServicesModule(),
            DummyIElevatorModule(),
            ViewModelsModule(),
            JavaFxModule(),
            MockPollingModule()
        )

    @JvmStatic
    fun provideApp(): InjectedApp {
        val injector = createMockInjector()
        injector.getInstance(UpdateElevatorStoreTask::class.java).run()

        val app = App()
        app.injectorFactory = { injector }
        return InjectedApp(app, injector)
    }
}