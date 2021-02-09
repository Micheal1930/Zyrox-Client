package com.zyrox.client.gameframe;

public class GameframeConstants {

	public static int getOrbColor(int value, int maxValue) {
		int percentage = value / maxValue * 100;
		if (percentage >= 75) {
			return 65280;
		} else if (percentage >= 50) {
			return 0xffff00;
		} else if (percentage >= 25) {
			return 0xfca607;
		} else if (percentage >= 0) {
			return 0xf50d0d;
		}
		return 0xffffff;
	}

}
