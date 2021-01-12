
package frc.robot.auto;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

/*
    *   o is the hyp length, l is the angle
    */
public class POI {

    public float o, l;
    public int actions;

    public POI() {
    }

    /*
     * l is the hyp length, o is the angle
     */
    public POI(float l, float o) {
        this.o = o;
        this.l = l;
    }

    public POI(float l, float o, int actions) {
        this.o = o;
        this.l = l;
        this.actions = actions;
    }

    public boolean done = false;

    public void Start() {
        RobotPostion.Zero();
        doneMove = false;
        doneRot = false;
        doneActions = false;
        done = false;
        actionIndex = 0;
        startedAction = false;
        moveStarted = false;
    }

    boolean doneRot = false;
    boolean doneMove = false;
    boolean doneActions = false;
    int actionIndex = 0;

    public void Loop() {
        if (!this.done) {
            /*
            if (!this.doneRot) {
                if (this.Rotate()) {
                    this.doneRot = true;
                    RobotPostion.Zero();
                    System.out.println("Done Rotation");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {}
                }
            }
            */
            //if (this.doneRot && !this.doneMove) {
            if (!this.doneMove) {
                if (this.Move()) {
                    this.doneMove = true;
                    RobotPostion.Zero();
                    System.out.println("Done Movement");
                }
            }
            //if (this.doneRot && this.doneMove && !this.doneActions) {
            if (this.doneMove && !this.doneActions) {
                if (this.actions != 0) {
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
            //if (this.doneMove && this.doneRot && this.doneActions) {
            if (this.doneMove && this.doneRot && this.doneActions) {
                this.done = true;
                System.out.println("Done Poi");
            }
        } else {
            Robot.driveTrain.tankDrive(0, 0);
        }
        
    }

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
