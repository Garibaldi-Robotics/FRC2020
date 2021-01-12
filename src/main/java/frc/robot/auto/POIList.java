package frc.robot.auto;

import java.util.ArrayList;

import frc.robot.Robot;

/*
* This class is a list of POIs, and contains methods that execute them one at a time
*/
public class POIList {

    public ArrayList<POI> list = new ArrayList<POI>(); // The list of POIs to be executed
    public boolean running = false; // Is this list running right now?
    int index = -1; // Index of the current POI to be run

    // Initializer
    public POIList() {
        this.list = new ArrayList<POI>();
    }

    // Initializer, but it takes in a pre existing list of POIs
    public POIList(ArrayList<POI> list) {
        this.list = list;
    }

    // Call this bad boy to start the execution of the POIs
    public void Run() {
        this.running = true; // This list is now running
        this.index = 0; // Start executing at the first (or zeroth) POI

        Robot.activePOI = list.get(index); // Set the active POI to the first one
        Robot.activePOI.Start(); // Start the first POI
    }
    
    // This needs to be called periodically in order for stuff to work
    public void Loop() {

        // If this list isn't running, do nothing. If we are active, run the current POI's Loop() method
        if (!this.running) {
            return;
        } else {
            Robot.activePOI.Loop();
        }
        
        // If the current POI is finished, 
        if (Robot.activePOI.done == true) {
            System.out.println("Done index " + index + ", length of " + this.list.size()); // notify the extremely smart guy reading the console.

            // If the current POI isn't the last one, 
            if (this.list.size() -1 != this.index) {

                // Next. POI.
                this.index++;
                Robot.activePOI = list.get(index);
                Robot.activePOI.Start();
            } else { // If it is the last one, stop stuff
                this.index = -1;
                this.running = false;
                System.out.println("Done POI list"); // and tell the unfathomably humble guy reading the console that we have finished the POI list
            }
            

        }


        
    }

    // Methods for adding, removing and removing POIs from the list
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
