package frc.robot.Input;

public class Input {

    public static boolean GetButtonDown(Button button) {
        return button.getDown();
    }
    public static boolean GetButtonUp(Button button) {
        return button.getUp();
    }
    public static boolean GetButton(Button button) {
        return button.get();
    }
}