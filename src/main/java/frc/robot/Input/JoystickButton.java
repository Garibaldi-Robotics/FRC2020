package frc.robot.Input;

import frc.robot.Robot;

public enum JoystickButton implements Button {
    TRIGGER{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(1); } public boolean get(){ return Robot.joystick.getRawButton(1); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(1); } }, 
    SIDE{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(2); } public boolean get(){ return Robot.joystick.getRawButton(2); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(2); } }, 
    BUTTON03{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(3); } public boolean get(){ return Robot.joystick.getRawButton(3); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(3); } }, 
    BUTTON04{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(4); } public boolean get(){ return Robot.joystick.getRawButton(4); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(4); } }, 
    BUTTON05{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(5); } public boolean get(){ return Robot.joystick.getRawButton(5); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(5); } }, 
    BUTTON06{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(6); } public boolean get(){ return Robot.joystick.getRawButton(6); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(6); } }, 
    BUTTON07{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(7); } public boolean get(){ return Robot.joystick.getRawButton(7); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(7); } }, 
    BUTTON08{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(8); } public boolean get(){ return Robot.joystick.getRawButton(8); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(8); } }, 
    BUTTON09{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(9); } public boolean get(){ return Robot.joystick.getRawButton(9); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(9); } }, 
    BUTTON10{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(10); } public boolean get(){ return Robot.joystick.getRawButton(10); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(10); } }, 
    BUTTON11{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(11); } public boolean get(){ return Robot.joystick.getRawButton(11); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(11); } }, 
    BUTTON12{ public boolean getDown(){ return Robot.joystick.getRawButtonPressed(12); } public boolean get(){ return Robot.joystick.getRawButton(12); } public boolean getUp(){ return Robot.joystick.getRawButtonReleased(12); } }, 
    HAT_TOP{ public boolean getDown(){ if (Robot.joystick.getPOV() == 0) { return true; } else { return false; } } public boolean get(){ if (Robot.joystick.getPOV() == 0) { return true; } else { return false; } } public boolean getUp(){ if (Robot.joystick.getPOV() == 0) { return true; } else { return false; } } }, 
    HAT_RIGHT{ public boolean getDown(){ if (Robot.joystick.getPOV() == 90) { return true; } else { return false; } } public boolean get(){ if (Robot.joystick.getPOV() == 90) { return true; } else { return false; } } public boolean getUp(){ if (Robot.joystick.getPOV() == 90) { return true; } else { return false; } } }, 
    HAT_BACK{ public boolean getDown(){ if (Robot.joystick.getPOV() == 180) { return true; } else { return false; } } public boolean get(){ if (Robot.joystick.getPOV() == 180) { return true; } else { return false; } } public boolean getUp(){ if (Robot.joystick.getPOV() == 180) { return true; } else { return false; } } }, 
    HAT_LEFT{ public boolean getDown(){ if (Robot.joystick.getPOV() == -90) { return true; } else { return false; } } public boolean get(){ if (Robot.joystick.getPOV() == -90) { return true; } else { return false; } } public boolean getUp(){ if (Robot.joystick.getPOV() == -90) { return true; } else { return false; } } }, 
    HAT_TOPRIGHT{ public boolean getDown(){ if (Robot.joystick.getPOV() == 45) { return true; } else { return false; } } public boolean get(){ if (Robot.joystick.getPOV() == 45) { return true; } else { return false; } } public boolean getUp(){ if (Robot.joystick.getPOV() == 45) { return true; } else { return false; } } }, 
    HAT_BACKRIGHT{ public boolean getDown(){ if (Robot.joystick.getPOV() == 135) { return true; } else { return false; } } public boolean get(){ if (Robot.joystick.getPOV() == 135) { return true; } else { return false; } } public boolean getUp(){ if (Robot.joystick.getPOV() == 135) { return true; } else { return false; } } }, 
    HAT_BACKLEFT{ public boolean getDown(){ if (Robot.joystick.getPOV() == -135) { return true; } else { return false; } } public boolean get(){ if (Robot.joystick.getPOV() == -135) { return true; } else { return false; } } public boolean getUp(){ if (Robot.joystick.getPOV() == -135) { return true; } else { return false; } } }, 
    HAT_TOPLEFT{ public boolean getDown(){ if (Robot.joystick.getPOV() == -45) { return true; } else { return false; } } public boolean get(){ if (Robot.joystick.getPOV() == -45) { return true; } else { return false; } } public boolean getUp(){ if (Robot.joystick.getPOV() == -45) { return true; } else { return false; } } };
    
    
}
