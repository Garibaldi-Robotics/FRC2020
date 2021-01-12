package frc.robot.auto;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class RobotPostion {

    private static AHRS navX;
    public static CANEncoder encoder1, encoder2, encoder3, encoder4;

    public static double dstMoved;
    public static double rotation;

    static double rotOffset = 180;

    final double WHEEL_RADIUS = 47.88;
    final double WHEEL_GEARBOX = 10.75;

    public void Init() {
        navX = new AHRS(SerialPort.Port.kMXP);

        encoder1 = Robot.driveMotor1.getEncoder();
        encoder2 = Robot.driveMotor2.getEncoder();
        encoder3 = Robot.driveMotor3.getEncoder();
        encoder4 = Robot.driveMotor4.getEncoder();
    }

    public void Loop() {
        double yaw = navX.getYaw();
        SmartDashboard.putNumber("yaw", navX.getYaw());
        SmartDashboard.putNumber("pitch", navX.getPitch());
        SmartDashboard.putNumber("roll", navX.getRoll());
        yaw += Math.abs(rotOffset);
        SmartDashboard.putNumber("rotation", yaw);
        SmartDashboard.putNumber("offset", rotOffset);

        rotation = yaw;
        
        double pos1 = encoder1.getPosition();
        //double pos2 = encoder2.getPosition();
        //double rightSideMoved = (((pos1 + pos2) / 2) / WHEEL_GEARBOX) * WHEEL_RADIUS;
        pos1 = encoder3.getPosition();
        //pos2 = encoder4.getPosition(); 
        double leftSideMoved = (pos1 / WHEEL_GEARBOX) * WHEEL_RADIUS;

        //double totalMove = (leftSideMoved + rightSideMoved) / 2;
        dstMoved = leftSideMoved;
        SmartDashboard.putNumber("cm Moved", dstMoved);

    }

    public static void DirOffset() {
        rotOffset = (int) rotation - rotOffset;
    }


    public static void Zero() {
        encoder1.setPosition(0);
        encoder2.setPosition(0);
        encoder3.setPosition(0);
        encoder4.setPosition(0);
        dstMoved = 0;

    }
    
}
