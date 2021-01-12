package frc.robot;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Indexing extends Loopable {
    Encoder encoder = new Encoder(0, 1, false, EncodingType.k1X);
    VictorSPX motor = new VictorSPX(11);

    boolean cache;
    ServerSocket serverSocket;
    Socket server;
    DataInputStream in;
    /*
    public void Init() {
        
        try {
            serverSocket = new ServerSocket(7173);
            server = serverSocket.accept();
            server.setKeepAlive(true);
            server.setTcpNoDelay(true);
            in = new DataInputStream(server.getInputStream());
        } catch (IOException e) {}
        
    }

    int dir = 1;
    public void Loop() {
        System.out.println(encoder.getDistance());
        boolean detectBall;

        try {
             detectBall = new Joystick(0).getRawButton(3);
            //in.readBoolean();
            if (!cache && detectBall) {
                System.out.println("Ball detected");
                encoder.reset();
                cache = true;
            }
            if (cache && !detectBall) {
                cache = false;
            }
        } catch (Exception e) {}
        

        int rot = 40000;
        if (Math.abs(encoder.getDistance()) < rot && dir == 1) {
            motor.set(ControlMode.PercentOutput, 0.5);
        } else if (dir == 1) {
            dir *= -1;
            encoder.reset();
        }
        if (Math.abs(encoder.getDistance()) < rot && dir == -1) {
            motor.set(ControlMode.PercentOutput, -0.5);
        } else if (dir == -1) {
            dir *= -1;
            encoder.reset();
        }

    }
    */
}
