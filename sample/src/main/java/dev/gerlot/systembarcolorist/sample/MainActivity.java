package dev.gerlot.systembarcolorist.sample;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentResultOwner;
import androidx.lifecycle.LifecycleOwner;

import dev.gerlot.systembarcolorist.SystemBarColorist;
import dev.gerlot.systembarcolorist.sample.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    public static final String KEY_TOP_COLOR = "top_color";
    public static final String KEY_BOTTOM_COLOR = "bottom_color";
    private final String INITIAL_TOP_COLOR_STRING = "#FFCCCCCC";
    private final String INITIAL_BOTTOM_COLOR_STRING = "#FF0000FF";

    private int topColor = Color.parseColor(INITIAL_TOP_COLOR_STRING);
    private int bottomColor = Color.parseColor(INITIAL_BOTTOM_COLOR_STRING);

    private final FragmentResultListener colorPickerFragmentResultListener = (requestKey, result) -> {
        final int colorResult = result.getInt(ColorPickerFragment.RESULT_COLOR, -1);
        if (colorResult != -1) {
            if (ColorPickerFragment.REQUEST_KEY_TOP_COLOR.equals(requestKey)) {
                topColor = colorResult;
            } else if (ColorPickerFragment.REQUEST_KEY_BOTTOM_COLOR.equals(requestKey)) {
                bottomColor = colorResult;
            }
            onColorsChanged(topColor, bottomColor);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            final int savedTopColor = savedInstanceState.getInt(KEY_TOP_COLOR, -1);
            if (savedTopColor != -1) {
                topColor = savedTopColor;
            }
            final int savedBottomColor = savedInstanceState.getInt(KEY_BOTTOM_COLOR, -1);
            if (savedBottomColor != -1) {
                bottomColor = savedBottomColor;
            }
        }

        binding.topColorButton.setOnClickListener(v -> ColorPickerFragment.newInstance(ColorPickerFragment.REQUEST_KEY_TOP_COLOR, topColor).show(getSupportFragmentManager(), ColorPickerFragment.TAG));
        binding.bottomColorButton.setOnClickListener(v -> ColorPickerFragment.newInstance(ColorPickerFragment.REQUEST_KEY_BOTTOM_COLOR, bottomColor).show(getSupportFragmentManager(), ColorPickerFragment.TAG));

        onColorsChanged(topColor, bottomColor);

        getSupportFragmentManager().setFragmentResultListener(ColorPickerFragment.REQUEST_KEY_TOP_COLOR, this, colorPickerFragmentResultListener);
        getSupportFragmentManager().setFragmentResultListener(ColorPickerFragment.REQUEST_KEY_BOTTOM_COLOR, this, colorPickerFragmentResultListener);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY_TOP_COLOR, topColor);
        outState.putInt(KEY_BOTTOM_COLOR, bottomColor);
        super.onSaveInstanceState(outState);
    }

    private void onColorsChanged(final int topColorInt, final int bottomColorInt) {
        binding.topColorButton.setText("#" + Integer.toHexString(topColorInt));
        binding.bottomColorButton.setText("#" + Integer.toHexString(bottomColorInt));
        setGradientBackground(topColorInt, bottomColorInt);
    }

    private void setGradientBackground(final int topColorInt, final int bottomColorInt) {
        final GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { topColorInt, bottomColorInt });
        gd.setCornerRadius(0f);
        binding.getRoot().setBackground(gd);
        SystemBarColorist.colorSystemBarsOfWindow(getWindow(), topColorInt, bottomColorInt);
    }

}
