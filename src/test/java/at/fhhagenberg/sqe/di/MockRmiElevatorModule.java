package at.fhhagenberg.sqe.di;

import sqelevator.IElevator;
import com.google.inject.*;

public class MockRmiElevatorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Key.get(IElevator.class, RealIElevator.class)).toProvider(RealIElevatorMockProvider.class).in(Singleton.class);
    }
}