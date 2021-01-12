package frc.robot.Input;

public interface Button {
    public default boolean getDown() {
        return false;
    }
    public default boolean get() {
        return false;
    }
    public default boolean getUp() {
        return false;
    }
}