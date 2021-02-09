package com.zyrox.client;

public class RandomColor {
	
    private static int currentLoadingColor;
    private static int nextLoadingColor;
    private static long startTime;
    private static long colorStart;
    public static int currentColour;
    
    static {
        RandomColor.currentLoadingColor = -1;
        RandomColor.nextLoadingColor = -1;
        RandomColor.startTime = 0L;
        RandomColor.colorStart = 0L;
    }
    
    public static void process() {
        RandomColor.currentColour = processRandomColour();
    }
    
    private static int randomColor() {
        int red = (int)(Math.random() * 17.0);
        int green = (int)(Math.random() * 17.0);
        int blue = (int)(Math.random() * 17.0);
        int type = (int)(Math.random() * 8.0);
        if (type == 0) {
            type |= 1 << (int)(Math.random() * 3.0);
        }
        else if (type == 7) {
            type &= ~(1 << (int)(Math.random() * 3.0));
        }
        if ((type & 0x1) != 0x0) {
            red += 196 + (int)(Math.random() * 33.0);
        }
        if ((type & 0x2) != 0x0) {
            green += 196 + (int)(Math.random() * 33.0);
        }
        if ((type & 0x4) != 0x0) {
            blue += 196 + (int)(Math.random() * 33.0);
        }
        return red << 16 | green << 8 | blue;
    }
    
    private static int blend(final int dst, final int src, final int a1) {
        if (a1 <= 0) {
            return dst;
        }
        if (a1 >= 255) {
            return src;
        }
        final int a2 = 255 - a1;
        return (((0xFF00FF00 & (0xFF00FF & src) * a1) | (0xFF0000 & (src & 0xFF00) * a1)) >>> 8) + (((0xFF0000 & a2 * (dst & 0xFF00)) | (a2 * (dst & 0xFF00FF) & 0xFF00FF00)) >>> 8);
    }
    
    private static int processRandomColour() {
        final long time = System.currentTimeMillis();
        if (RandomColor.startTime == 0L) {
            RandomColor.startTime = time;
        }
        int n = -1;
        final long elapse = (time - RandomColor.startTime) / 30L;
        if (n == -1) {
            n = (int)(elapse % 2000L);
            if (n > 1000) {
                n = 2000 - n;
            }
        }
        if (n < 0) {
            n = 0;
        } else if (n > 1000) {
            n = 1000;
        }
        if (RandomColor.colorStart == 0L || RandomColor.nextLoadingColor == -1) {
            RandomColor.colorStart = time;
            RandomColor.nextLoadingColor = randomColor();
        }
        if (RandomColor.currentLoadingColor == -1) {
            RandomColor.currentLoadingColor = 3329330;
        }
        final long colorElapse = (time - RandomColor.colorStart) / 25L;
        final int alpha = (int)(colorElapse * 255L / 200L);
        final int color = blend(RandomColor.currentLoadingColor, RandomColor.nextLoadingColor, alpha);
        if (alpha >= 255) {
            RandomColor.currentLoadingColor = RandomColor.nextLoadingColor;
            RandomColor.nextLoadingColor = -1;
        }
        return rgbToHSL(color);
    }
    
    public static int rgbToHSL(final int color) {
        final double r = (color >> 16 & 0xFF) / 256.0;
        final double g = (color >> 8 & 0xFF) / 256.0;
        final double b = (color & 0xFF) / 256.0;
        double red_val1 = r;
        if (g < red_val1) {
            red_val1 = g;
        }
        if (b < red_val1) {
            red_val1 = b;
        }
        double red_val2 = r;
        if (g > red_val2) {
            red_val2 = g;
        }
        if (b > red_val2) {
            red_val2 = b;
        }
        double hueCalc = 0.0;
        double satCalc = 0.0;
        final double lightCalc = (red_val1 + red_val2) / 2.0;
        if (red_val1 != red_val2) {
            if (lightCalc < 0.5) {
                satCalc = (red_val2 - red_val1) / (red_val2 + red_val1);
            }
            if (lightCalc >= 0.5) {
                satCalc = (red_val2 - red_val1) / (2.0 - red_val2 - red_val1);
            }
            if (r == red_val2) {
                hueCalc = (g - b) / (red_val2 - red_val1);
            }
            else if (g == red_val2) {
                hueCalc = 2.0 + (b - r) / (red_val2 - red_val1);
            }
            else if (b == red_val2) {
                hueCalc = 4.0 + (r - g) / (red_val2 - red_val1);
            }
        }
        hueCalc /= 6.0;
        int saturation = (int)(satCalc * 256.0);
        int lightness = (int)(lightCalc * 256.0);
        if (saturation < 0) {
            saturation = 0;
        }
        else if (saturation > 255) {
            saturation = 255;
        }
        if (lightness < 0) {
            lightness = 0;
        }
        else if (lightness > 255) {
            lightness = 255;
        }
        int divisor = 1;
        if (lightCalc > 0.5) {
            divisor = (int)((1.0 - lightCalc) * satCalc * 512.0);
        }
        else {
            divisor = (int)(lightCalc * satCalc * 512.0);
        }
        if (divisor < 1) {
            divisor = 1;
        }
        final int hueOffset = (int)(hueCalc * divisor);
        final int saturationOffset = saturation;
        final int lightnessOffset = lightness;
        return getHSLValue(hueOffset, saturationOffset, lightnessOffset);
    }
    
    private static int getHSLValue(final int hue, int saturation, final int lightness) {
        if (lightness > 179) {
            saturation /= 2;
        }
        if (lightness > 192) {
            saturation /= 2;
        }
        if (lightness > 217) {
            saturation /= 2;
        }
        if (lightness > 243) {
            saturation /= 2;
        }
        return (hue / 4 << 10) + (saturation / 32 << 7) + lightness / 2;
    }
}