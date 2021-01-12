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

  public static POI activePOI; // A global variable that stores the information of the POI that the robot is currently navigation to
  POIList list = new POIList(); // Stores the list of POIs that the robot will execute during auto mode

  // The controllers (idk why they are called Joysticks, when they are the whole controller)
  public static Joystick joystick = new Joystick(0);
  public static Joystick buttonPanel = new Joystick(1);

  // Spark MAX motor controllers, these are used for the drive train
  public static CANSparkMax driveMotor1 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax driveMotor2 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax driveMotor3 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax driveMotor4 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);

  // Setting up the speed controller groups and the DifferentialDrive, this allows the drive train (which consists of 4 motors, 2 on each side) to be controlled with ease
  public static SpeedControllerGroup driveLeft = new SpeedControllerGroup(driveMotor2, driveMotor3);
  public static SpeedControllerGroup driveRight = new SpeedControllerGroup(driveMotor1, driveMotor4);
  public static DifferentialDrive driveTrain = new DifferentialDrive(driveLeft, driveRight);

  // Create instances of the classes that are in charge of tracking the robot's position during auto mode and the operation of the control panel, respectevly 
  public RobotPostion robotPostion = new RobotPostion();
  public WheelOf4Chan fourChan = new WheelOf4Chan();
  
  // The motor controllers used for reeling in the climber, and releasing the hook
  public TalonSRX alsoClimber = new TalonSRX(15);
  public TalonSRX climber = new TalonSRX(16);
  public VictorSPX climberRelease = new VictorSPX(7);

  
  public PWM actuator = new PWM(1); // This is for the actuator that pushes the control panel spinner into position (I think)
  public VictorSPX spinner = new VictorSPX(8);  // The motor controller for the wheel that spins the control panel

  public static VictorSPX roller = new VictorSPX(12); // The motor controller for the roller that brings balls into the conveyor system
  public static VictorSPX hopper = new VictorSPX(11); // The motor controller for the main conveyor system
  
  // The cameras
  UsbCamera ballCam;
  UsbCamera frontCam;

  // This method is called when the robot is powered on (important note: powered on DOES NOT mean enabled)
  @Override
  public void robotInit() {

    // Setting up the cameras and telling them what resolution and framerate to run at
    ballCam = CameraServer.getInstance().startAutomaticCapture(0);
    ballCam.setResolution(160, 120);
    ballCam.setFPS(15);
    frontCam = CameraServer.getInstance().startAutomaticCapture(1);
    frontCam.setResolution(640, 480);
    frontCam.setFPS(30);
    
    // Try to read the .csv file which contains the instructions for the robot to execute in auto mode. If for whatever reason, it faild, throw an error but don't crash the program
    try {
      ReadCSV.LoadAutoFromProgram();
    } catch (Exception e) {
      e.printStackTrace();
    }
    robotPostion.Init(); // Initialize the robot's positoin tracking

    list.running = false; // Make sure that the poi system is not running

    list.Add(new POI(-310, 0, 0)); // Move backward and deposit balls

  }

  // This method is called periodically when the robot is powered on (important note: powered on DOES NOT mean enabled)
  @Override
  public void robotPeriodic() {
    robotPostion.Loop(); // Run the position tracking's main loop
  }

  // This method is called at the start of autonomous mode
  @Override
  public void autonomousInit() {

    // Zero the robot's percieved position... no idea why there is two different methods for this but here we are
    RobotPostion.Zero();
    RobotPostion.DirOffset();
    
    list.Run(); // Start the POI system with the POIs that are in the list
  }

  // This method is called periodically during the autonomous mode
  @Override
  public void autonomousPeriodic() {
    list.Loop(); // Loop the POI system
  }

  // This method is called at the start of the teleop (remote controled) mode. (This wasn't used but is included because you might forget or smth)
  @Override
  public void teleopInit() {
  }


  public int servoPos = 0; // Current position of the servo, which is actually the actuator
  boolean driveDir = true; // This is used to keep track of the current driving direction of the robot (there is a button that reverses front and back, for enhanced driver precision)

  // This method is called periodically during the teleop mode
  @Override
  public void teleopPeriodic() {

    // Controls the intake roller
    if (buttonPanel.getRawButton(11)) {
      roller.set(ControlMode.PercentOutput, 0.5); // Forwards
    } else if (buttonPanel.getRawButton(12)) {
      roller.set(ControlMode.PercentOutput, -0.5); // Backwards
    } else {
      roller.set(ControlMode.PercentOutput, 0); // Stop
    }

    // Controls the conveyor belts, which act as both shooter and storage for the balls
    if (buttonPanel.getRawButton(3)) {
      hopper.set(ControlMode.PercentOutput, 1); // Eject and take in balls, fast
    } else if (buttonPanel.getRawButton(4)) {
      hopper.set(ControlMode.PercentOutput, -0.5); // Eject and take ing balls, but slower
    } else if (buttonPanel.getRawButton(9)) {
      hopper.set(ControlMode.PercentOutput, 0.5); // Reverse, just in case
    }else {
      hopper.set(ControlMode.PercentOutput, 0); // Stop
    }

    // Button that reverses the "front" of the robot, to allow for increased manouverability
    if (joystick.getRawButtonPressed(1)) {
      driveDir = !driveDir;
    }

    // This is what turns joystick into motor spinning... boring, right? they do it for you... Maybe we can make a better one this year
    // Oh yeah, this also accounts for the current "front" of the robot
    if (driveDir) {
      driveTrain.arcadeDrive(-joystick.getY(), joystick.getX());
    } else {
      driveTrain.arcadeDrive(joystick.getY(), joystick.getX());
    }
    
    // Controls the climber motors
    if (buttonPanel.getRawButton(10)) {
      // Real in
      alsoClimber.set(ControlMode.PercentOutput, -0.5);
      climber.set(ControlMode.PercentOutput, -0.5);
    } else {
      // Do not move...
      alsoClimber.set(ControlMode.PercentOutput, 0);
      climber.set(ControlMode.PercentOutput, 0);
    }
    
    // Button to relase the climber 
    if (buttonPanel.getRawButton(6)) {
      climberRelease.set(ControlMode.PercentOutput, 0.5); // Open latch (I think)
    } else if (buttonPanel.getRawButton(5)) {
      climberRelease.set(ControlMode.PercentOutput, -0.5); // Close latch (I think)
    } else {
      climberRelease.set(ControlMode.PercentOutput, 0); // Don't
    }


    // Controls the position of the roller (up or down)
    if (buttonPanel.getRawButtonPressed(8)) {

      // Toggle between fully extended and fully retracted
      if (servoPos == 0) {
        servoPos = 360;
      } else {
        servoPos = -360;
      }
    }
    actuator.setSpeed(servoPos); // Move the actuator to the desired position
    
    
    // Fancy thing that loops all of the classes that need to be
    for (Loopable curr : Loopable.list) {
      curr.Loop();
    }
  }
}
