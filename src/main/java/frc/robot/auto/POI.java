
package frc.robot.auto;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

/*
*   A class that stores the releveant information and methods for a single point of interest (POI)
*   These are used during the auto mode to move the robot and complete acitons
*/
public class POI {

    // Each POI stores 3 variables, the angle to turn to (o), the distance to drive (l), and the actions to complete (acitons) (Executed in that order)
    public float o, l;
    public int actions;

    // Empty initializer
    public POI() {}

    /*
    *   Initialize a new POI, without actions
    *   l is the hyp length, o is the angle
    */
    public POI(float l, float o) {
        this.o = o;
        this.l = l;
    }

    /*
    *   Initialize a new POI, with actions
    *   l is the hyp length, o is the angle
    */
    public POI(float l, float o, int actions) {
        this.o = o;
        this.l = l;
        this.actions = actions;
    }

    public boolean done = false; // Keeps track whether or not this POI is finished its execution
    boolean doneRot = false; // Keeps track of whether or not the robot has reached the desired rotation
    boolean doneMove = false; // Keeps track of whether or not the robot has reached the desired location
    boolean doneActions = false; // Keeps track of whether or not the robot has done the actions in this POI
    int actionIndex = 0; // Keeps track of the current action that the robot is executing

    // This method is called to start the execution of this POI
    public void Start() {
        RobotPostion.Zero(); // Zero the robot position, so we can keep track of how far we move RELATIVE to the previous position

        // Reset all of the variables that are used to tell the state of this POI
        doneMove = false;
        doneRot = false;
        doneActions = false;
        done = false;
        actionIndex = 0;
        startedAction = false;
        moveStarted = false;
    }

    // This method is called periodically during the execution of this POI
    public void Loop() {

        if (!this.done) { // Make sure that this POI isn't already finished

            // If the move step isn't completed,
            if (!this.doneMove) {
                if (this.Move()) { // move, and if we have reached the destination, 
                    this.doneMove = true; // mark down that we have and
                    RobotPostion.Zero(); // zero the position
                }
            }

            // If the move step is completed, but the action step isn't, 
            if (this.doneMove && !this.doneActions) {
                if (this.actions != 0) { // check if there is any actions associated with this POI, and if there is,

                    // Do whatever the hell this is... Which moron programmed this cursed process?
                    // I have no idea why I did this, but there must've been one... maybe
                    char[] actionList = (this.actions + "").toCharArray();
                    if (this.Action(Integer.parseInt(actionList[actionIndex] + ""))) {
                        if (actionList.length -1 == actionIndex) {
                            doneActions = true;
                        } else {
                            actionIndex++;
                        }
                    }
                }
            }
            
            // If the move, rotation and action steps are all completed, mark down that we are done, and debug it for Arden's sanity
            if (this.doneMove && this.doneRot && this.doneActions) {
                this.done = true;
                System.out.println("Done Poi");
            }
        } else {
            Robot.driveTrain.tankDrive(0, 0); // If we are done, stop moving
        }
        
    }

    // Handles the actions, which consist of depositing the balls stored in the robot... there were going to be more, but we ran out of time
    // Not going to bother commenting this one
    boolean startedAction = false;
    Timer actionTimer = new Timer();
    boolean Action(int _action) {
        if (_action == 1) {
            if (!startedAction) {
                startedAction = true;
                actionTimer.reset();
                actionTimer.start();
            }
            
            if (actionTimer.get() >= 3) {
                Robot.hopper.set(ControlMode.PercentOutput, 0);
                return true;
            } else {
                Robot.hopper.set(ControlMode.PercentOutput, 1);
                return false;
            }
        }
        return true;
    }

    // Handles the rotation of the robot. However, it wasn't used as far as I can see in the code, and I think it had something to do with the NavX not working
    // Also not going to bother commenting this one
    float direction = -1;
    boolean Rotate() {
        float robotation = (float)RobotPostion.rotation;
        float destination = this.o;

        if (this.direction == -1) {
            this.direction = destination - robotation;
            while (this.direction < 0) {
                this.direction += 360;
            }
        }

        float speed = Math.abs(robotation - destination);
        if (this.direction > 180) {
            //turn right
            Robot.driveTrain.tankDrive(NormalizeRot(speed), -NormalizeRot(speed));
            
        } else if (this.direction < 180) {
            //turn left
            Robot.driveTrain.tankDrive(-NormalizeRot(speed), NormalizeRot(speed));

        }
        if (robotation < destination + 2 && robotation > destination - 2) {
            Robot.driveTrain.tankDrive(0, 0);
            return true;
        }

        return false;
    }

    // Handles the moving of the robot to the desired location
    // I'm also also not going to comment this one, mostly because I don't feel like it... If you're interested and want an explanation, I can provide one
    Timer moveTimer = new Timer();
    boolean moveStarted = false;
    boolean Move() {
        if (!moveStarted) {
            moveTimer.reset();
            moveTimer.start();
            moveStarted = true;
        } else {
            if (moveTimer.get() >= 10) {
                moveTimer.stop();
                return true;
            }
        }
        

        if (RobotPostion.dstMoved < (this.l + 3) && RobotPostion.dstMoved > (this.l - 3)) {
            return true;
        }

        float speed = (float)(this.l - RobotPostion.dstMoved);
        if (RobotPostion.dstMoved < this.l) {
            Robot.driveTrain.tankDrive(NormalizeMov(speed, this.l), NormalizeMov(speed, this.l));
            return false;
        } else if (RobotPostion.dstMoved > this.l) {
            Robot.driveTrain.tankDrive(-NormalizeMov(speed, this.l), -NormalizeMov(speed, this.l));
            return false;
        }
        return true;
    }

    // This was used to limit the motors between two speeds, due to reasons
    float NormalizeRot(float value) {
        float x = (value - 0) / (360 - 0);
        if (x < 0.35f) {
            x = 0.35f;
        }
        if(x > 0.6f) {
            x = 0.6f;
        }
        return x;
    }

    // This was used to limit the motors between two speeds, due to reasons
    float NormalizeMov(float value, float max) {
        float x = (value - 0) / (max - 0);
        if (x < 0.35f) {
            x = 0.45f;
        }
        if(x > 0.8f) {
            x = 0.8f;
        }
        return x;
    }

}
