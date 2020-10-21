package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.ElevatorControlSystem;

import java.rmi.RemoteException;

public interface ElevatorControlSystemService {
    public ElevatorControlSystem get() throws RemoteException;
}