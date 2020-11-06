package at.fhhagenberg.sqe.di

import com.google.inject.AbstractModule

class RmiElevatorModule : AbstractModule() {
    override fun configure() {
        // TODO: Bind IElevator to RMI IElevator implementation
        // bind(Key.get(IElevator.class, RealIElevator.class)).to().in(Singleton.class);
    }
}