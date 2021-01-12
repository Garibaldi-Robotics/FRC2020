/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;

public class SendVideo extends Loopable{

    Socket client;
    OutputStream output;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    BufferedImage frame;

    @Override
    public void Init() {
        try {
            
            client = new Socket("10.71.73.234", 6969);
            System.out.println("Connected to CameraServer");
            output = client.getOutputStream();




        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void Loop() {

        if (client.isConnected()) {
            try {

                frame = MatToBI(DriverCam.frame);
                ImageIO.write(frame, "jpg", baos);
                baos.flush();
                byte[] img = baos.toByteArray();
                baos.close();
    
                output.write(img);
                output.flush();
    
    
    
            } catch (IOException e) {e.printStackTrace();}
        } else {
            try {
            
                client = new Socket("10.71.73.234", 6969);
                System.out.println("Connected to CameraServer");
                output = client.getOutputStream();
    
    
    
    
            } catch (Exception e) {e.printStackTrace();}
        }


        
        
    }


    private BufferedImage MatToBI(Mat frame) throws IOException {
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }

}
