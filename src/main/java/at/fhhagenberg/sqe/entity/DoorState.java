package at.fhhagenberg.sqe.entity;

public enum DoorState {

    OPEN(1),
    CLOSED(2),
    OPENING(3),
    CLOSING(4);

    public final int doorState;

    DoorState(int doorState) {
        this.doorState = doorState;
    }

    public static DoorState valueOf(int doorState) {
        for (DoorState enumDoorState : values()) {
            if (enumDoorState.doorState == doorState) {
                return enumDoorState;
            }
        }
        return null;
    }
}