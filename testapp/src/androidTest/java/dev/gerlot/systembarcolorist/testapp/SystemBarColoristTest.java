package dev.gerlot.systembarcolorist.testapp;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowInsetsController;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import dev.gerlot.systembarcolorist.SystemBarColorist;
import dev.gerlot.systembarcolorist.util.ColorUtil.ColorDarkness;

public class SystemBarColoristTest {

    private void assertBarColorsCorrectlySet(final Activity activity,
                                             final ColorDarkness statusBarColorDarkness,
                                             final ColorDarkness navigationBarColorDarkness) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final int systemBarsAppearance = activity.getWindow().getInsetsController().getSystemBarsAppearance();
            if (statusBarColorDarkness == ColorDarkness.BRIGHT && navigationBarColorDarkness == ColorDarkness.BRIGHT) {
                assertEquals(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS | WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, systemBarsAppearance);
            } else if (statusBarColorDarkness == ColorDarkness.DARK && navigationBarColorDarkness == ColorDarkness.DARK) {
                assertEquals(0, systemBarsAppearance);
            } else if (statusBarColorDarkness == ColorDarkness.DARK && navigationBarColorDarkness == ColorDarkness.BRIGHT) {
                assertEquals(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, systemBarsAppearance);
            } else {
                assertEquals(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, systemBarsAppearance);
            }
        } else {
            final int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (statusBarColorDarkness == ColorDarkness.BRIGHT && navigationBarColorDarkness == ColorDarkness.BRIGHT) {
                    assertEquals(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, systemUiVisibility);
                } else if (statusBarColorDarkness == ColorDarkness.DARK && navigationBarColorDarkness == ColorDarkness.DARK) {
                    assertEquals(0 & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR & ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, systemUiVisibility);
                } else if (statusBarColorDarkness == ColorDarkness.DARK && navigationBarColorDarkness == ColorDarkness.BRIGHT) {
                    assertEquals(0 & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, systemUiVisibility);
                } else {
                    assertEquals(0 & ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, systemUiVisibility);
                }
            } else {
                if (statusBarColorDarkness == ColorDarkness.BRIGHT) {
                    assertEquals(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, systemUiVisibility);
                } else {
                    assertEquals(0 & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, systemUiVisibility);
                }
            }
        }
    }

    @Test
    public void given_valid_color_for_status_bar_When_setting_status_bar_color_Then_it_is_correctly_set() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final int colorInt = Color.parseColor("#000000");

            try (final ActivityScenario<TestActivity> activityScenario = ActivityScenario.launch(TestActivity.class)) {
                activityScenario.onActivity(activity -> {
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), colorInt, Color.TRANSPARENT);

                    assertEquals(colorInt, activity.getWindow().getStatusBarColor());
                });
            }

            final int colorInt2 = Color.parseColor("#FFFFFF");

            try (final ActivityScenario<TestActivity> activityScenario = ActivityScenario.launch(TestActivity.class)) {
                activityScenario.onActivity(activity -> {
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), colorInt2, Color.TRANSPARENT);

                    assertEquals(colorInt2, activity.getWindow().getStatusBarColor());
                });
            }

            final int colorInt3 = Color.GREEN;

            try (final ActivityScenario<TestActivity> activityScenario = ActivityScenario.launch(TestActivity.class)) {
                activityScenario.onActivity(activity -> {
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), colorInt3, Color.TRANSPARENT);

                    assertEquals(colorInt3, activity.getWindow().getStatusBarColor());
                });
            }
        }
    }

    @Test
    public void given_valid_color_for_navigation_bar_When_setting_navigation_bar_color_Then_it_is_correctly_set() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final int colorInt = Color.parseColor("#000000");

            try (final ActivityScenario<TestActivity> activityScenario = ActivityScenario.launch(TestActivity.class)) {
                activityScenario.onActivity(activity -> {
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), Color.TRANSPARENT, colorInt);

                    assertEquals(colorInt, activity.getWindow().getNavigationBarColor());
                });
            }

            final int colorInt2 = Color.parseColor("#FFFFFF");

            try (final ActivityScenario<TestActivity> activityScenario = ActivityScenario.launch(TestActivity.class)) {
                activityScenario.onActivity(activity -> {
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), Color.TRANSPARENT, colorInt2);

                    assertEquals(colorInt2, activity.getWindow().getNavigationBarColor());
                });
            }

            final int colorInt3 = Color.CYAN;

            try (final ActivityScenario<TestActivity> activityScenario = ActivityScenario.launch(TestActivity.class)) {
                activityScenario.onActivity(activity -> {
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), Color.TRANSPARENT, colorInt3);

                    assertEquals(colorInt3, activity.getWindow().getNavigationBarColor());
                });
            }
        }
    }
    @Test
    public void given_light_color_for_both_status_bar_and_navigation_bar_When_setting_system_bar_backgrounds_Then_bright_color_is_set_to_foreground_text() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final int colorInt = Color.parseColor("#FFFFFF");

            try (final ActivityScenario<TestActivity> activityScenario = ActivityScenario.launch(TestActivity.class)) {
                activityScenario.onActivity(activity -> {
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), colorInt, colorInt);

                    assertBarColorsCorrectlySet(activity, ColorDarkness.BRIGHT, ColorDarkness.BRIGHT);
                });
            }
        }
    }

    @Test
    public void given_dark_color_for_both_status_bar_and_navigation_bar_When_setting_system_bar_backgrounds_Then_bright_color_is_set_to_foreground_text() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final int colorInt = Color.parseColor("#000000");

            try (final ActivityScenario<TestActivity> activityScenario = ActivityScenario.launch(TestActivity.class)) {
                activityScenario.onActivity(activity -> {
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), colorInt, colorInt);

                    assertBarColorsCorrectlySet(activity, ColorDarkness.DARK, ColorDarkness.DARK);
                });
            }
        }
    }

    @Test
    public void given_dark_color_for_status_bar_and_light_color_for_navigation_bar_When_setting_system_bar_backgrounds_Then_bright_color_is_set_to_foreground_text() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final int statusBarColorInt = Color.parseColor("#000000");
            final int navigationBarColorInt = Color.parseColor("#FFFFFF");

            try (final ActivityScenario<TestActivity> activityScenario = ActivityScenario.launch(TestActivity.class)) {
                activityScenario.onActivity(activity -> {
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), statusBarColorInt, navigationBarColorInt);

                    assertBarColorsCorrectlySet(activity, ColorDarkness.DARK, ColorDarkness.BRIGHT);
                });
            }
        }
    }

    @Test
    public void given_light_color_for_status_bar_and_dark_color_for_navigation_bar_When_setting_system_bar_backgrounds_Then_bright_color_is_set_to_foreground_text() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final int statusBarColorInt = Color.parseColor("#FFFFFF");
            final int navigationBarColorInt = Color.parseColor("#000000");

            try (final ActivityScenario<TestActivity> activityScenario = ActivityScenario.launch(TestActivity.class)) {
                activityScenario.onActivity(activity -> {
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
                    SystemBarColorist.colorSystemBarsOfWindow(activity.getWindow(), statusBarColorInt, navigationBarColorInt);

                    assertBarColorsCorrectlySet(activity, ColorDarkness.BRIGHT, ColorDarkness.DARK);
                });
            }
        }
    }

}
