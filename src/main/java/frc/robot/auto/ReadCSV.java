package frc.robot.auto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Add your docs here.
 */
public class ReadCSV {
    public static File autoFile;
    public static boolean doneReading = false;
    public static POIList sequence;

    public static void LoadAutoFromFile() throws IOException {
        autoFile = new File("/home/lvuser/Auto.csv");
        autoFile.setReadable(true);

        doneReading = false;
        FileReader read = new FileReader(autoFile);
        BufferedReader reader = new BufferedReader(read);

        int lines = reader.lines().toArray().length;
        float[][] data = new float[lines][3]; //-1 because of the offset line that is only used by the plotter...

        
        //while (!reader.ready()) {}
        System.out.println("exists? " + autoFile.exists() + "\n" + lines + " lines");

        Object[] linings = Files.readAllLines(autoFile.toPath()).toArray();

        for (int i = 0; i < lines; i++) {
            String dataStr = linings[i].toString();
            System.out.println(dataStr);
            String[] dataArr = dataStr.split(",");
            for (int j = 0; j < dataArr.length; j++) {
                data[i][j] = Float.parseFloat(dataArr[j]);
            }
        }

        System.out.println("done");

        for (int i = 0; i < lines; i++) {
            data[i][0] = data[i][0] - data[i-1][0]; //Accounting for my stupidity
        }
        
        for (int i = 1; i < lines -1; i++) {
            sequence.Add(new POI(data[i][0], data[i][1], (int) data[i][2]));
        }

        reader.close();
        doneReading = true;

        for (POI poi : sequence.list) {
            System.out.println("Rotate to " + poi.o + " and then move " + poi.l + "cm");
        }
    }

    public static void LoadAutoFromProgram() {
        sequence = new POIList();
        sequence.list.add(new POI(350, 22));
        sequence.list.add(new POI(260, 90));
        doneReading = true;
    }

}
