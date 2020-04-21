package com.varrock.util;

import java.awt.Color;

public class MiscUtils {

	/**
	 * Methods from the internet used for counter progress bar
	 */
	public static int mixColors(Color color1, Color color2, double percent) {
		double inverse_percent = 1.0 - percent;
		int redPart = (int) (color1.getRed() * percent + color2.getRed() * inverse_percent);
		int greenPart = (int) (color1.getGreen() * percent + color2.getGreen() * inverse_percent);
		int bluePart = (int) (color1.getBlue() * percent + color2.getBlue() * inverse_percent);
		return getIntFromColor(redPart, greenPart, bluePart);
	}
	
    private static int getIntFromColor(int Red, int Green, int Blue){
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

}
