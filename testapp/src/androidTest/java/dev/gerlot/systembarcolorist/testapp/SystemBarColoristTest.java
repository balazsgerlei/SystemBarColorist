package dev.gerlot.systembarcolorist.testapp;

import static org.junit.Assert.assertEquals;

import android.graphics.Color;
import android.os.Build;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import dev.gerlot.systembarcolorist.SystemBarColorist;

public class SystemBarColoristTest {

    @Test
    public void given_valid_statusBarColor_When_setSystemBarBackgrounds_is_called_Then_window_statusBarColor_correctly_set() {
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

}
