package dev.gerlot.systembarcolorist.util;

import android.graphics.Color;

public class ColorUtil {

    public enum ColorDarkness {
        BRIGHT, DARK
    }

    public static ColorDarkness calculateColorDarkness(int color) {
        return calculateColorLuminance(color) < 0.5 ? ColorDarkness.BRIGHT : ColorDarkness.DARK;
    }

    public static double calculateColorLuminance(int color) {
        return 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
    }

}
