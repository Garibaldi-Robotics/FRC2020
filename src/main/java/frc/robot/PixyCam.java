/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import edu.wpi.cscore.CvSource;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2Video;
import io.github.pseudoresonance.pixy2api.RGB;
import io.github.pseudoresonance.pixy2api.links.SPILink;
import java.awt.Color;

public class PixyCam extends Loopable {

    public Pixy2 pixyCam;
    CvSource server;
    Pixy2Video video;
    Size resolution;
    public static Mat frame;

    @Override
    public void Init() {
        super.Init();

        pixyCam = Pixy2.createInstance(new SPILink());
        pixyCam.init();

        pixyCam.setLamp((byte) 0, (byte) 1);
        pixyCam.setLED(255, 255, 255);

        resolution = new Size(1296, 976);

        video = pixyCam.getVideo();
    }

    @Override
    public void Loop() {
        frame = MatFromVideo(video, (int) resolution.width, (int) resolution.height);
        

        
    }

    public void Stop() {
        pixyCam.close();
    }


    Mat MatFromVideo(Pixy2Video frame, int width, int height) {
        Mat mat = new Mat(width, height, CvType.CV_16UC1);
        for (int x = 0; x < width; x++) {
          for (int y = 0; y < height; y++) {
            RGB rgb = new RGB(0, 0, 0);
            frame.getRGB(x, y, rgb, false);
            mat.put(x, y, new Color(rgb.getR(), rgb.getG(), rgb.getB()).getRGB());
          }
        }
    
        return mat;
      }

    
}
