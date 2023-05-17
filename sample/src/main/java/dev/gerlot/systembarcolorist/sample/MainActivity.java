package dev.gerlot.systembarcolorist.sample;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import dev.gerlot.systembarcolorist.SystemBarColorist;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        final View backgroundView = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        if (backgroundView != null) {
            final int topColorInt = Color.LTGRAY;
            final int bottomColorInt = Color.BLUE;
            final GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { topColorInt, bottomColorInt });
            gd.setCornerRadius(0f);
            backgroundView.setBackground(gd);
            SystemBarColorist.colorSystemBarsOfWindow(getWindow(), topColorInt, bottomColorInt);
        }
    }

}
