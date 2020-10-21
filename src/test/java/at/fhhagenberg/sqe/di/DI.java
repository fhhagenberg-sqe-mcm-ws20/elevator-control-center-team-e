package at.fhhagenberg.sqe.di;

import com.google.inject.Guice;
import com.google.inject.Injector;

public final class DI {

    private static Injector getInjector() {
        return Guice.createInjector(
                new ElevatorServicesModule(),
                new MockRmiElevatorModule()
        );
    }

    public static <T> T get(Class<T> clazz) {
        return getInjector().getInstance(clazz);
    }
}