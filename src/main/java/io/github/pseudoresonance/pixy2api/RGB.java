
package io.github.pseudoresonance.pixy2api;
import java.awt.Color;

public class RGB {

    int r, g, b;

    /**
     * Constructs RGB container
     * 
     * @param r R value
     * @param g G value
     * @param b B value
     */
    public RGB(int r, int g, int b) {
        // Limits rgb values between the min and max
        this.r = (r >= 255 ? 255 : (r <= 0 ? 0 : r));
        this.g = (g >= 255 ? 255 : (g <= 0 ? 0 : g));
        this.b = (b >= 255 ? 255 : (b <= 0 ? 0 : b));
    }

    /**
     * @return Color object containing RGB
     */
    public Color getColor() {
        return new Color(r, g, b);
    }

    /**
     * @return R value
     */
    public byte getR() {
        return (byte) r;
    }

    /**
     * @return G value
     */
    public byte getG() {
        return (byte) g;
    }

    /**
     * @return B value
     */
    public byte getB() {
        return (byte) b;
    }

    /**
     * Sets R value between 0-255
     * 
     * @param r R value
     */
    public void setR(int r) {
        // Limits r value between the min and max
        this.r = (r >= 255 ? 255 : (r <= 0 ? 0 : r));
    }

    /**
     * Sets G value between 0-255
     * 
     * @param g G value
     */
    public void setG(int g) {
        // Limits g value between the min and max
        this.g = (g >= 255 ? 255 : (g <= 0 ? 0 : g));
    }

    /**
     * Sets B value between 0-255
     * 
     * @param b B value
     */
    public void setB(int b) {
        // Limits b value between the min and max
        this.b = (b >= 255 ? 255 : (b <= 0 ? 0 : b));
    }

    /**
     * Sets RGB value from Color
     * 
     * @param color Color
     */
    public void setRGB(Color color) {
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
    }

    /**
     * Sets RGB value
     * 
     * @param rgb RGB value
     */
    public void setRGB(int rgb) {
        this.r = (rgb >> 16) & 0xff;
        this.g = (rgb >> 8) & 0xff;
        this.b = rgb & 0xff;
    }

    /**
     * Sets RGB values between 0-255
     * 
     * @param r R value
     * @param g G value
     * @param b B value
     */
    public void setRGB(int r, int g, int b) {
        // Limits r value between the min and max
        this.r = (r >= 255 ? 255 : (r <= 0 ? 0 : r));
        this.g = (g >= 255 ? 255 : (g <= 0 ? 0 : g));
        this.b = (b >= 255 ? 255 : (b <= 0 ? 0 : b));
    }

}
