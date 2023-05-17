package dev.gerlot.systembarcolorist;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;

import dev.gerlot.systembarcolorist.util.ColorUtil;

public class SystemBarColorist {

    public static void colorStatusBarOfWindow(final Window window, final int statusBarColor) {
        colorSystemBarsOfWindow(window, statusBarColor, null);
    }

    public static void colorSystemBarsOfWindow(final Window window, final int statusBarColor, final int navigationBarColor) {
        colorSystemBarsOfWindow(window, Integer.valueOf(statusBarColor), Integer.valueOf(navigationBarColor));
    }

    public static void colorSystemBarsOfWindow(final Window window, Integer statusBarColor, Integer navigationBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final boolean isStatusBarColorBright = ColorUtil.calculateColorDarkness(statusBarColor) == ColorUtil.ColorDarkness.BRIGHT;

            window.setStatusBarColor(statusBarColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Below Android 8 the Navigation Bar cannot be colored
                window.setNavigationBarColor(navigationBarColor);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                final boolean isNavigationColorBright = ColorUtil.calculateColorDarkness(navigationBarColor) == ColorUtil.ColorDarkness.BRIGHT;
                final WindowInsetsController insetsController = window.getInsetsController();
                if (insetsController != null) {
                    final int statusBarAppearance = isStatusBarColorBright ? WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS : 0;
                    final int navigationBarAppearance = isNavigationColorBright ? WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS : 0;
                    insetsController.setSystemBarsAppearance(statusBarAppearance, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                    insetsController.setSystemBarsAppearance(navigationBarAppearance, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
                }
            } else {
                final View decorView = window.getDecorView();
                if (decorView != null) {
                    final boolean isNavigationColorBright = ColorUtil.calculateColorDarkness(navigationBarColor) == ColorUtil.ColorDarkness.BRIGHT;
                    final int currentSystemUiVisibility = decorView.getSystemUiVisibility();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if (isStatusBarColorBright && isNavigationColorBright) {
                            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                        } else if (!isStatusBarColorBright && !isNavigationColorBright) {
                            window.getDecorView().setSystemUiVisibility(currentSystemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR & ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                        }  else if (!isStatusBarColorBright) { // isNavigationColorBright is guaranteed to be true here
                            window.getDecorView().setSystemUiVisibility(currentSystemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                        } else { // the remaining case is when isStatusBarColorBright is true and isNavigationColorBright is false
                            window.getDecorView().setSystemUiVisibility(currentSystemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        }
                    } else { // Below Android 8 only the Status Bar can be colored
                        if (isStatusBarColorBright) {
                            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        } else {
                            window.getDecorView().setSystemUiVisibility(currentSystemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        }
                    }
                }
            }
        }
    }

}
