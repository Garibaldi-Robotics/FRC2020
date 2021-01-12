package io.github.pseudoresonance.pixy2api;


import java.util.concurrent.TimeUnit;

/**
 * Java Port of Pixy2 Arduino Library
 * 
 * Defines video getter for Pixy2
 * 
 * https://github.com/PseudoResonance/Pixy2JavaAPI
 * 
 * @author PseudoResonance (Josh Otake)
 *
 *         ORIGINAL HEADER -
 *         https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/Pixy2Video.h
 *         ==============================================================================================
 *         begin license header
 *
 *         This file is part of Pixy CMUcam5 or "Pixy" for short
 *
 *         All Pixy source code is provided under the terms of the GNU General
 *         Public License v2 (http://www.gnu.org/licenses/gpl-2.0.html). Those
 *         wishing to use Pixy source code, software and/or technologies under
 *         different licensing terms should contact us at cmucam@cs.cmu.edu.
 *         Such licensing terms are available for all portions of the Pixy
 *         codebase presented here.
 *
 *         end license header
 *
 *         This file is for defining the Block struct and the Pixy template
 *         class version 2. (TPixy2). TPixy takes a communication link as a
 *         template parameter so that all communication modes (SPI, I2C and
 *         UART) can share the same code.
 */

public class Pixy2Video {
	public final static byte VIDEO_REQUEST_GET_RGB = 0x70;

	private final Pixy2 pixy;

	/**
	 * Constructs Pixy2 video getter
	 * 
	 * @param pixy Pixy2 instance
	 */
	protected Pixy2Video(Pixy2 pixy) {
		this.pixy = pixy;
	}

	/**
	 * Gets average RGB value at 5x5 area around specified coordinates in the image
	 * 
	 * @param x        X value
	 * @param y        Y value
	 * @param rgb      RGB container to return values in
	 * @param saturate Whether or not to scale all RGB values to maximize the
	 *                 greatest value at 255
	 * 
	 * @return Pixy2 error code
	 */
	public int getRGB(int x, int y, RGB rgb, boolean saturate) {
		long start = System.currentTimeMillis();

		while (true) {
			pixy.bufferPayload[0] = (byte) (x & 0xff);
			pixy.bufferPayload[1] = (byte) ((x >> 8) & 0xff);
			pixy.bufferPayload[2] = (byte) (y & 0xff);
			pixy.bufferPayload[3] = (byte) ((y >> 8) & 0xff);
			pixy.bufferPayload[4] = (byte) (saturate == true ? 1 : 0);
			pixy.length = 5;
			pixy.type = VIDEO_REQUEST_GET_RGB;
			pixy.sendPacket();
			if (pixy.receivePacket() == 0) {
				if (pixy.type == Pixy2.PIXY_TYPE_RESPONSE_RESULT && pixy.length == 4) {
					rgb.setRGB(pixy.buffer[0], pixy.buffer[1], pixy.buffer[2]);
					return 0; // Success
				} else if (pixy.type == Pixy2.PIXY_TYPE_RESPONSE_ERROR
						&& pixy.buffer[0] == Pixy2.PIXY_RESULT_PROG_CHANGING) {
					// Deal with program changing by waiting
					try {
						TimeUnit.MICROSECONDS.sleep(500);
					} catch (InterruptedException e) {
					}
					continue;
				}
			}
			if (System.currentTimeMillis() - start > 500) {
				return Pixy2.PIXY_RESULT_ERROR; // Timeout to prevent lockup
			}
			return Pixy2.PIXY_RESULT_ERROR;
		}
	}

	

}