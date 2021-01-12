package frc.robot.Input;

import edu.wpi.first.wpilibj.Joystick;

public enum PanelButton implements Button {
    GREEN1{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(1); } public boolean get(){ return new Joystick(1).getRawButton(1); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(1); } }, 
    GREEN2{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(2); } public boolean get(){ return new Joystick(1).getRawButton(2); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(2); } }, 
    GREEN3{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(3); } public boolean get(){ return new Joystick(1).getRawButton(3); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(3); } }, 
    WHITE1{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(4); } public boolean get(){ return new Joystick(1).getRawButton(4); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(4); } }, 
    BLACK1{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(5); } public boolean get(){ return new Joystick(1).getRawButton(5); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(5); } }, 
    BLACK2{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(6); } public boolean get(){ return new Joystick(1).getRawButton(6); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(6); } }, 
    RED1{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(7); } public boolean get(){ return new Joystick(1).getRawButton(7); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(7); } }, 
    BLUE1{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(8); } public boolean get(){ return new Joystick(1).getRawButton(8); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(8); } }, 
    BLUE2{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(9); } public boolean get(){ return new Joystick(1).getRawButton(9); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(9); } }, 
    YELLOW1{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(10); } public boolean get(){ return new Joystick(1).getRawButton(10); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(10); } }, 
    YELLOW2{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(11); } public boolean get(){ return new Joystick(1).getRawButton(11); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(11); } }, 
    YELLOW3{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(12); } public boolean get(){ return new Joystick(1).getRawButton(12); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(12); } },
    WHITE2{ public boolean getDown(){ return new Joystick(1).getRawButtonPressed(13); } public boolean get(){ return new Joystick(1).getRawButton(13); } public boolean getUp(){ return new Joystick(1).getRawButtonReleased(13); } };
}