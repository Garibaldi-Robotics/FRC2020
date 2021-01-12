package frc.robot.auto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class is really cool, it reads a .csv file stored on the robot and makes a POIList out of it
 */
public class ReadCSV {
    public static File autoFile = new File("/home/lvuser/Auto.csv"); // the location of the file we gonna read from
    public static boolean doneReading = false; // are we done reading the file?
    public static POIList sequence; // output POIList object

    // Call this method to... hear me out on this... Load the Auto from the File
    public static void LoadAutoFromFile() throws IOException {
        autoFile.setReadable(true); // Make sure that we have permissions to read the file

        doneReading = false; // reset this
        BufferedReader reader = new BufferedReader(new FileReader(autoFile)); // Class used to read from the file

        int lines = reader.lines().toArray().length; // how many lines are in the file?
        float[][] data = new float[lines][3]; // Storage of the parced data from the file

        System.out.println("exists? " + autoFile.exists() + "\n" + lines + " lines"); // A debug message for (you get the joke now)

        Object[] linings = Files.readAllLines(autoFile.toPath()).toArray(); // All of the lines in the file, stored as a seperate variable in an array (No idea why it's called "linings", but whatever) (Also, the reason Object[] was used instead of String[] is because I'm lazy)

        // Parse each line into parts, and store each in the 'data' array
        for (int i = 0; i < lines; i++) {
            String dataStr = linings[i].toString();
            System.out.println(dataStr);
            String[] dataArr = dataStr.split(",");
            for (int j = 0; j < dataArr.length; j++) {
                data[i][j] = Float.parseFloat(dataArr[j]);
            }
        }

        System.out.println("done"); // Debugging

        // No idea what this does, and I don't feel like figureing it out
        for (int i = 0; i < lines; i++) {
            data[i][0] = data[i][0] - data[i-1][0]; //Accounting for my stupidity <- OG comment from past Arden
        }
        
        // For each line, add a new POI using the data we parsed earlier
        for (int i = 1; i < lines -1; i++) {
            sequence.Add(new POI(data[i][0], data[i][1], (int) data[i][2]));
        }

        reader.close(); // Close the file reader (this should be done earlier)
        doneReading = true; // mark down that we are done

        // Debugging
        for (POI poi : sequence.list) {
            System.out.println("Rotate to " + poi.o + " and then move " + poi.l + "cm");
        }
    }

    // This method is if something went wrong and we just want to make a sequence using 'new POI()'
    public static void LoadAutoFromProgram() {
        sequence = new POIList();
        sequence.list.add(new POI(350, 22));
        sequence.list.add(new POI(260, 90));
        doneReading = true;
    }

}
