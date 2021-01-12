package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Servo;

public class WheelOf4Chan extends Loopable{
    Servo actuator = new Servo(0);
    double actuatorPos = 0;

    VictorSPX spinThing = new VictorSPX(5);
    

    @Override
    public void Loop() {
        super.Loop();
        actuatorPos = actuator.get();
    }

    public void Extend() {
        for (double i = actuatorPos; i < 360; i++) {
            actuator.set(i);
        }
        
        spinThing.set(ControlMode.PercentOutput, 100);
        Retract();
    }

    // colour: blue=1, red=2, green=3, yellow=4
    public void Extend(int colour) {
        for (double i = actuatorPos; i < 360; i++) {
            actuator.set(i);
        }
        
        spinThing.set(ControlMode.PercentOutput, 100);
        Retract();
    }

    public void Retract() {
        for (double i = actuatorPos; i > 0; i--) {
            actuator.set(i);
        }
    }


    int GetColour() {
        return -1;
    }
}
