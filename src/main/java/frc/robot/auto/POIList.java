/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import java.util.ArrayList;

import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class POIList {
    public ArrayList<POI> list = new ArrayList<POI>();
    public boolean running = false;

    public POIList() {
        this.list = new ArrayList<POI>();
    }
    public POIList(ArrayList<POI> list) {
        this.list = list;
    }

    public void Run() {
        this.running = true;
        this.index = 0;
        Robot.activePOI = list.get(index);
        Robot.activePOI.Start();
    }

    int index = -1;
    
    public void Loop() {
        if (!this.running) {
            return;
        } else {
            Robot.activePOI.Loop();
        }
        
        if (Robot.activePOI.done == true) {
            System.out.println("Done index " + index + ", length of " + this.list.size());

            if (this.list.size() -1 != this.index) {
                this.index++;
                Robot.activePOI = list.get(index);
                Robot.activePOI.Start();
            } else {
                this.index = -1;
                this.running = false;
                System.out.println("Done Auto");
            }
            

        }


        
    }

    public void Add(POI poi) {
        this.list.add(poi);
    }
    public void Remove(POI poi) {
        this.list.remove(poi);
    }
    public void Remove(int index) {
        this.list.remove(index);
    }
}
