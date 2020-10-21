package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.BuildingFloor;

import java.rmi.RemoteException;
import java.util.List;

public interface BuildingFloorService {
    public List<BuildingFloor> getAll(int totalNumberOfFloors) throws RemoteException;
    public BuildingFloor get(int floorNumber) throws RemoteException;
}