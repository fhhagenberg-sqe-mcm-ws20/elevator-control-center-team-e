package at.fhhagenberg.sqe.di;

import at.fhhagenberg.sqe.IElevator;
import at.fhhagenberg.sqe.api.CachedElevatorControl;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

public class RmiElevatorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IElevator.class).to(CachedElevatorControl.class);
    }

    @Provides
    @RealIElevator
    public IElevator provideRealIElevator(Injector injector) {
        throw new RuntimeException("Not implemented!");
        // TODO: Bind IElevator to RMI IElevator implementation
        // return injector.getInstance(MockElevatorControl.class);
    }
}