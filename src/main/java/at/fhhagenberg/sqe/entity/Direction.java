package at.fhhagenberg.sqe.entity;

public enum Direction {

    UP(0),
    DOWN(1),
    UNCOMMITTED(2);

    public final int direction;

    Direction(int direction) {
        this.direction = direction;
    }

    public static Direction valueOf(int direction) {
        for (Direction enumDirection : values()) {
            if (enumDirection.direction == direction) {
                return enumDirection;
            }
        }
        return null;
    }
}