package at.fhhagenberg.sqe.di;

import at.fhhagenberg.sqe.entity.*;
import at.fhhagenberg.sqe.rmi.IElevator;
import at.fhhagenberg.sqe.rmi.MockElevatorControl;
import com.google.inject.AbstractModule;

public class MockRmiElevatorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IElevator.class).to(MockElevatorControl.class);
        bind(ElevatorControlSystem.class).toProvider(ElevatorControlSystemProvider.class);
    }
}