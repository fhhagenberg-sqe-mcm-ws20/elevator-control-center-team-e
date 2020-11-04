package at.fhhagenberg.sqe.di;

import com.google.inject.*;

public class RmiElevatorModule extends AbstractModule {
    @Override
    protected void configure() {
        // TODO: Bind IElevator to RMI IElevator implementation
        // bind(Key.get(IElevator.class, RealIElevator.class)).to().in(Singleton.class);
    }
}