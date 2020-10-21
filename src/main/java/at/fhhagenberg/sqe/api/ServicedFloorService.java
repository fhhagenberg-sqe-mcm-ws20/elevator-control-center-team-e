package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.ServicedFloor;

import java.rmi.RemoteException;
import java.util.List;

public interface ServicedFloorService {
    public List<ServicedFloor> getAll(int elevatorNumber) throws RemoteException;
    public ServicedFloor get(int elevatorNumber, int floorNumber) throws RemoteException;
    public void updateServicedFloor(ServicedFloor servicedFloor) throws RemoteException;
}