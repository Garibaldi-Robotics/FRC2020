package frc.robot.auto;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/*
*   This class keeps track of the robot's position and rotation, according to the encoders built into the NEO motors and the NavX (RIP NavX)
*/
public class RobotPostion {

    private static AHRS navX; // the NavX that caused more problems than it solved
    public static CANEncoder encoder1, encoder2, encoder3, encoder4; // All of the encoders

    public static double dstMoved; // the distance moved
    public static double rotation; // the current rotation

    static double rotOffset = 180; // Offset for the rotation

    // these are important for the calculation of the distance the robot has moved
    final double WHEEL_RADIUS = 47.88; // Radius of the wheels, in... a unit of measurement (probably mm if I had to guess)
    final double WHEEL_GEARBOX = 10.75; // The ratio of the gearbox attached to the drive motors

    // Called to initialize the position and rotation tracking
    public void Init() {
        navX = new AHRS(SerialPort.Port.kMXP); // Init the navX

        // Init the encoders (this bit isn't done globally because it uses the controllers declaired in the main 'Robot' class)
        encoder1 = Robot.driveMotor1.getEncoder();
        encoder2 = Robot.driveMotor2.getEncoder();
        encoder3 = Robot.driveMotor3.getEncoder();
        encoder4 = Robot.driveMotor4.getEncoder();
    }

    // Called periodically
    public void Loop() {

        // Get the angle data from the NavX and put it on the driver dashboard (for debugging)
        double yaw = navX.getYaw();
        SmartDashboard.putNumber("yaw", navX.getYaw());
        SmartDashboard.putNumber("pitch", navX.getPitch());
        SmartDashboard.putNumber("roll", navX.getRoll());
        yaw += Math.abs(rotOffset);
        SmartDashboard.putNumber("rotation", yaw);
        SmartDashboard.putNumber("offset", rotOffset);

        rotation = yaw; // Why not just use 1 variable?
        
        // Get the encoder data and do maths to it
        // This was originally much more complex and cooler, using all 4 encoders, but was scrapped for whatever reason
        dstMoved = (encoder1.getPosition() / WHEEL_GEARBOX) * WHEEL_RADIUS;

        SmartDashboard.putNumber("cm Moved", dstMoved); // Debugging!
    }

    // Why?
    public static void DirOffset() {
        rotOffset = (int) rotation - rotOffset;
    }


    // Used to zero the position of the robot
    public static void Zero() {
        encoder1.setPosition(0);
        encoder2.setPosition(0);
        encoder3.setPosition(0);
        encoder4.setPosition(0);
        dstMoved = 0;

    }
    
}
