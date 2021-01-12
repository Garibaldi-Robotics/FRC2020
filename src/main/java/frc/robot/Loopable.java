package frc.robot;

import java.util.ArrayList;

public class Loopable {
    public static ArrayList<Loopable> list = new ArrayList<Loopable>();

    public Loopable() {
        System.out.println("<" + this + "> ~demonic screeching~");
        list.add(this);
        Init();
    }

    public void Loop() {
    }

    public void Init() {
    }
}
