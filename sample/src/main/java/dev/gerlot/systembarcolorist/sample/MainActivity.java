package dev.gerlot.systembarcolorist.sample;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import dev.gerlot.systembarcolorist.SystemBarColorist;
import dev.gerlot.systembarcolorist.sample.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    private final String INITIAL_TOP_COLOR_STRING = "#FFCCCCCC";
    private final String INITIAL_BOTTOM_COLOR_STRING = "#FF0000FF";

    private int topColor = Color.parseColor(INITIAL_TOP_COLOR_STRING);
    private int bottomColor = Color.parseColor(INITIAL_BOTTOM_COLOR_STRING);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().setFragmentResultListener(ColorPickerFragment.REQUEST_KEY_TOP_COLOR, this, (requestKey, bundle) -> {
            topColor = bundle.getInt(ColorPickerFragment.RESULT_COLOR);
            binding.topColorButton.setText("#" + Integer.toHexString(topColor));
            setGradientBackground(topColor, bottomColor);
        });
        getSupportFragmentManager().setFragmentResultListener(ColorPickerFragment.REQUEST_KEY_BOTTOM_COLOR, this, (requestKey, bundle) -> {
            bottomColor = bundle.getInt(ColorPickerFragment.RESULT_COLOR);
            binding.bottomColorButton.setText("#" + Integer.toHexString(bottomColor));
            setGradientBackground(topColor, bottomColor);
        });


        binding.topColorButton.setText("#" + Integer.toHexString(topColor));
        binding.topColorButton.setOnClickListener(v -> {
            ColorPickerFragment.newInstance(ColorPickerFragment.REQUEST_KEY_TOP_COLOR, topColor).show(getSupportFragmentManager(), ColorPickerFragment.TAG);
        });
        binding.bottomColorButton.setText("#" + Integer.toHexString(bottomColor));
        binding.bottomColorButton.setOnClickListener(v -> {
            ColorPickerFragment.newInstance(ColorPickerFragment.REQUEST_KEY_BOTTOM_COLOR, bottomColor).show(getSupportFragmentManager(), ColorPickerFragment.TAG);
        });

        setGradientBackground(topColor, bottomColor);
    }

    private void setGradientBackground(final int topColorInt, final int bottomColorInt) {
        final GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { topColorInt, bottomColorInt });
        gd.setCornerRadius(0f);
        binding.getRoot().setBackground(gd);
        SystemBarColorist.colorSystemBarsOfWindow(getWindow(), topColorInt, bottomColorInt);
    }

}
