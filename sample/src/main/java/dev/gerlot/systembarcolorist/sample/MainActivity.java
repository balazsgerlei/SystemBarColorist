package dev.gerlot.systembarcolorist.sample;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import dev.gerlot.systembarcolorist.SystemBarColorist;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        final View backgroundView = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        if (backgroundView != null) {
            final String topColorString = "#FFCCCCCC";
            final int topColorInt = Color.parseColor(topColorString);
            final Button topColorButton = findViewById(R.id.topColorButton);
            topColorButton.setText(topColorString);
            final String bottomColorString = "#FF0000FF";
            final int bottomColorInt = Color.parseColor(bottomColorString);
            final Button bottomColorButton = findViewById(R.id.bottomColorButton);
            bottomColorButton.setText(bottomColorString);
            final GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { topColorInt, bottomColorInt });
            gd.setCornerRadius(0f);
            backgroundView.setBackground(gd);
            SystemBarColorist.colorSystemBarsOfWindow(getWindow(), topColorInt, bottomColorInt);
        }
    }

}
