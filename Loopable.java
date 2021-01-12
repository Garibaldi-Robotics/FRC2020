package frc.robot;

import java.util.ArrayList;

/*
*   A class that can be extended from which allows for the clean looking code in the main class which doesn't contain eight million different Class.Loop() things
*   You don't need to worry about this, just realize that I'm cool for making it
*/
public class Loopable {
    public static ArrayList<Loopable> list = new ArrayList<Loopable>();

    public Loopable() {
        list.add(this);
        Init();
    }

    public void Loop() {
    }

    public void Init() {
    }
}
