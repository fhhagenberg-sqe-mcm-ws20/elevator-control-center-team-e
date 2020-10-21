package at.fhhagenberg.sqe.di;

import at.fhhagenberg.sqe.api.*;
import com.google.inject.AbstractModule;

public class ElevatorServicesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BuildingFloorService.class).to(BuildingFloorServiceImpl.class);
        bind(ElevatorControlSystemService.class).to(ElevatorControlSystemServiceImpl.class);
        bind(ElevatorService.class).to(ElevatorServiceImpl.class);
        bind(FloorButtonService.class).to(FloorButtonServiceImpl.class);
        bind(ServicedFloorService.class).to(ServicedFloorServiceImpl.class);
    }
}
