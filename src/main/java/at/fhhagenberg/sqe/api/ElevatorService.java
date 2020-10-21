package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.Elevator;
import java.rmi.RemoteException;
import java.util.List;

public interface ElevatorService {
    public List<Elevator> getAll() throws RemoteException;
    public Elevator get(int elevatorNumber) throws RemoteException;
    public void updateCommittedDirection(Elevator elevator) throws RemoteException;
    public void updateTargetFloor(Elevator elevator) throws RemoteException;
}