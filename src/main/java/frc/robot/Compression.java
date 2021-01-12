package frc.robot;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Compression {
    int thanos = 1;

    public BufferedImage BigBrain(BufferedImage image) {
        int imageLength = image.getWidth();
        int imageHeight = image.getHeight();

        for (int x = 0; x < imageLength ; x++) {
            for (int y = 0; y < imageHeight ; y++) {

                Color colour = new Color(image.getRGB(x,y));
                Color newColour = Color.white;
                int red = colour.getRed(), green = colour.getGreen(), blue = colour.getBlue();


                int newred = 0, newgreen = 0, newblue = 0;
                if (red >= blue && red >= green) {
                    newred = 255;
                } else
                if (blue >= red && blue >= green) {
                    newblue = 255;
                } else
                if (green >= red && green >= blue) {
                    newgreen = 255;
                }
                /*
                if (red >= 128) {
                    newred = 255;
                }
                if (blue >= 128) {
                    newblue = 255;
                }
                if (green >= 128) {
                    newgreen = 255;
                }
    */


                newColour = new Color(newred, newgreen, newblue);
                image.setRGB(x, y, newColour.getRGB());
            }
        }
        return image;
    }

    public BufferedImage SmallBrain(BufferedImage image) {
        int imageLength = image.getWidth();
        int imageHeight = image.getHeight();

        for (int x = 0; x < imageLength ; x++) {
            for (int y = 0; y < imageHeight ; y++) {

                Color colour = new Color(image.getRGB(x,y));
                Color newColour = Color.white;
                int red = colour.getRed(), green = colour.getGreen(), blue = colour.getBlue();


                //#region big brain
                /*
                int newred = 0, newgreen = 0, newblue = 0;
                if (red >= blue && red >= green) {
                    newred = 255;
                } else if (blue >= red && blue >= green) {
                    newblue = 255;
                } else if (green >= red && green >= blue) {
                    newgreen = 255;
                }
                ///
                if (red >= 128) {
                    newred = 255;
                }
                if (blue >= 128) {
                    newblue = 255;
                }
                if (green >= 128) {
                    newgreen = 255;
                }
                */
                //#endregion

                red /= 16;
                green /= 16;
                blue /= 16;
                red *= 16;
                green *= 16;
                blue *= 16;


                newColour = new Color(red, green, blue);
                /*
                if (i % 2 == thanos) {
                    newColour = new Color(128, 128, 128);
                }

                */
                image.setRGB(x, y, newColour.getRGB());
            }
        }
        return image;
    }
}
