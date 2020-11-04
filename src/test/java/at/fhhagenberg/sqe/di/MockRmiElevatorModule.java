package at.fhhagenberg.sqe.di;

import at.fhhagenberg.sqe.entity.*;
import sqelevator.IElevator;
import at.fhhagenberg.sqe.MockElevatorControl;
import com.google.inject.*;

public class MockRmiElevatorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ElevatorControlSystem.class).toProvider(ElevatorControlSystemProvider.class).in(Singleton.class);
    }

    @Provides
    @RealIElevator
    public IElevator provideRealIElevator(Injector injector) {
        return injector.getInstance(MockElevatorControl.class);
    }
}