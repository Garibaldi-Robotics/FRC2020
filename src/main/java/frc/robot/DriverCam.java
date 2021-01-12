/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import edu.wpi.cscore.UsbCamera;

/**
 * Add your docs here.
 */
public class DriverCam extends Loopable{

    VideoCapture capture;
    public static Mat frame;
    UsbCamera cam;

    @Override
    public void Init() {
        try {
            capture = new VideoCapture(0);
            cam = new UsbCamera("cam0", 0);
        } catch (Exception e) {System.out.println("ERROR");}
        
    }

    @Override
    public void Loop() {
        try {
            if (capture.isOpened()) {
                capture.read(frame);
            } else {
                capture.open(0);
            }
        } catch (Exception e) {}
        
    }

}
