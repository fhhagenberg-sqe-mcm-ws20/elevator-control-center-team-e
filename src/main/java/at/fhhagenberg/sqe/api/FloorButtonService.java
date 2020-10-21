package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.FloorButton;

import java.rmi.RemoteException;
import java.util.List;

public interface FloorButtonService {
    public List<FloorButton> getAll(int elevatorNumber) throws RemoteException;
    public FloorButton get(int elevatorNumber, int floorNumber) throws RemoteException;
}