package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.auto.POI;
import frc.robot.auto.POIList;
import frc.robot.auto.ReadCSV;
import frc.robot.auto.RobotPostion;

public class Robot extends TimedRobot {




  public static POI activePOI;

  public static Joystick joystick = new Joystick(0);
  public static Joystick buttonPanel = new Joystick(1);


  public static CANSparkMax driveMotor1 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax driveMotor2 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax driveMotor3 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax driveMotor4 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);

  public static SpeedControllerGroup driveLeft = new SpeedControllerGroup(driveMotor2, driveMotor3);
  public static SpeedControllerGroup driveRight = new SpeedControllerGroup(driveMotor1, driveMotor4);
  public static DifferentialDrive driveTrain = new DifferentialDrive(driveLeft, driveRight);

  public RobotPostion robotPostion = new RobotPostion();
  public WheelOf4Chan fourChan = new WheelOf4Chan();
  
  // public DriverCam driverCam = new DriverCam();
  // public SendVideo video = new SendVideo();
  public TalonSRX alsoClimber = new TalonSRX(15);
  public TalonSRX climber = new TalonSRX(16);
  public VictorSPX climberRelease = new VictorSPX(7);
  public PWM actuator = new PWM(1);
  public static VictorSPX roller = new VictorSPX(12);
  public static VictorSPX hopper = new VictorSPX(11);
  public VictorSPX spinner = new VictorSPX(8);

  UsbCamera ballCam;
  UsbCamera frontCam;
  @Override
  public void robotInit() {
    ballCam = CameraServer.getInstance().startAutomaticCapture(0);
    ballCam.setResolution(160, 120);
    ballCam.setFPS(15);
    frontCam = CameraServer.getInstance().startAutomaticCapture(1);
    frontCam.setResolution(640, 480);
    frontCam.setFPS(30);
    
    try {
      ReadCSV.LoadAutoFromProgram();
    } catch (Exception e) {
      e.printStackTrace();
    }
    robotPostion.Init();

    list.running = false;

    list.Add(new POI(-310, 0, 0));// Move backward and deposit balls

  }

  
  @Override
  public void robotPeriodic() {
    robotPostion.Loop();
    
  }

  POIList list = new POIList();
  @Override
  public void autonomousInit() {
    RobotPostion.Zero();
    RobotPostion.DirOffset();
    
    list.Run();
  }

  @Override
  public void autonomousPeriodic() {
    //ReadCSV.sequence.Loop();
    
    list.Loop();
    //robotPostion.Loop();
  }

  @Override
  public void teleopInit() {
  }

  public int servoPos = 0;
  boolean driveDir = true;
  @Override
  public void teleopPeriodic() {

    // Intake roller
    if (buttonPanel.getRawButton(11)) {
      roller.set(ControlMode.PercentOutput, 0.5);
    } else if (buttonPanel.getRawButton(12)) {
      roller.set(ControlMode.PercentOutput, -0.5);
    } else {
      roller.set(ControlMode.PercentOutput, 0);
    }

    if (buttonPanel.getRawButton(3)) {
      hopper.set(ControlMode.PercentOutput, 1);
    } else if (buttonPanel.getRawButton(4)) {
      hopper.set(ControlMode.PercentOutput, -0.5);
    } else if (buttonPanel.getRawButton(9)) {
      hopper.set(ControlMode.PercentOutput, 0.5);
    }else {
      hopper.set(ControlMode.PercentOutput, 0);
    }


    if (joystick.getRawButtonPressed(1)) {
      driveDir = !driveDir;
    }

    if (driveDir) {
      driveTrain.arcadeDrive(-joystick.getY(), joystick.getX());
    } else {
      driveTrain.arcadeDrive(joystick.getY(), joystick.getX());
    }
    
    // Climber
    if (buttonPanel.getRawButton(10)) {
      alsoClimber.set(ControlMode.PercentOutput, -0.5);
      climber.set(ControlMode.PercentOutput, -0.5);
    } else {
      alsoClimber.set(ControlMode.PercentOutput, 0);
      climber.set(ControlMode.PercentOutput, 0);
    }
    
    // TEMP climber release
    if (buttonPanel.getRawButton(6)) {
      climberRelease.set(ControlMode.PercentOutput, 0.5);
    } else if (buttonPanel.getRawButton(5)) {
      climberRelease.set(ControlMode.PercentOutput, -0.5);
    } else {
      climberRelease.set(ControlMode.PercentOutput, 0);
    }

    /* TEMP Roller spin
    if (Input.GetButtonDown(b_WheelOFRotation)) {
      spinner.set(ControlMode.PercentOutput, 1);
    } else {
      spinner.set(ControlMode.PercentOutput, 0);
    }
    */
    // Roller state
    if (buttonPanel.getRawButtonPressed(8)) {
      if (servoPos == 0) {
        servoPos = 360;
      } else {
        servoPos = -360;
      }
      System.out.println(servoPos);
    }
    actuator.setSpeed(servoPos);
    
    

    for (Loopable curr : Loopable.list) {
      curr.Loop();
    }
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit() {
  }
}
