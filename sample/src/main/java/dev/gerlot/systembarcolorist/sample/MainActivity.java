package dev.gerlot.systembarcolorist.sample;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import dev.gerlot.systembarcolorist.SystemBarColorist;
import dev.gerlot.systembarcolorist.sample.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final String topColorString = "#FFCCCCCC";
        final int topColorInt = Color.parseColor(topColorString);
        binding.topColorButton.setText(topColorString);
        binding.topColorButton.setOnClickListener(v -> {
            // TODO
        });
        final String bottomColorString = "#FF0000FF";
        final int bottomColorInt = Color.parseColor(bottomColorString);
        binding.bottomColorButton.setText(bottomColorString);
        binding.bottomColorButton.setOnClickListener(v -> {
            // TODO
        });

        final GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { topColorInt, bottomColorInt });
        gd.setCornerRadius(0f);
        binding.getRoot().setBackground(gd);
        SystemBarColorist.colorSystemBarsOfWindow(getWindow(), topColorInt, bottomColorInt);
    }

}
