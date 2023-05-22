package dev.gerlot.systembarcolorist.sample;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import dev.gerlot.systembarcolorist.sample.databinding.ColorPickerFragmentBinding;

public class ColorPickerFragment extends DialogFragment {

    public static final String TAG = ColorPickerFragment.class.getSimpleName();

    public static final String REQUEST_KEY_TOP_COLOR = "top_color";
    public static final String REQUEST_KEY_BOTTOM_COLOR = "bottom_color";
    public static final String RESULT_COLOR = "color";

    private static final String ARG_REQUEST_KEY = "request_key";
    private static final String ARG_COLOR = "color";
    private static final String KEY_REQUEST_KEY = "request_key";
    private static final String KEY_COLOR = "color";

    private ColorPickerFragmentBinding binding;

    private String requestKey;
    private int color;

    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                onColorChanged(binding.redSeekBar.getProgress(), binding.greenSeekBar.getProgress(), binding.blueSeekBar.getProgress());
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public ColorPickerFragment() {
    }

    public static ColorPickerFragment newInstance(final String requestKey, final int color) {
        final ColorPickerFragment fragment = new ColorPickerFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_REQUEST_KEY, requestKey);
        args.putInt(ARG_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = ColorPickerFragmentBinding.inflate(requireActivity().getLayoutInflater());
        return new AlertDialog.Builder(requireContext())
                .setView(binding.getRoot())
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    final Bundle result = new Bundle();
                    result.putInt(RESULT_COLOR, color);
                    getParentFragmentManager().setFragmentResult(requestKey, result);
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {})
                .create();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) binding = ColorPickerFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.colorView.setBackgroundColor(color);

        binding.redSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        binding.greenSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        binding.blueSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        int colorFromBundle = -1;
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REQUEST_KEY) && savedInstanceState.containsKey(KEY_COLOR)) {
            requestKey = savedInstanceState.getString(KEY_REQUEST_KEY);
            colorFromBundle = savedInstanceState.getInt(KEY_COLOR);
        } else if (getArguments() != null) {
            requestKey = getArguments().getString(ARG_REQUEST_KEY);
            colorFromBundle = getArguments().getInt(ARG_COLOR);
        }

        if (colorFromBundle != -1) {
            final int redValue = Color.red(colorFromBundle);
            final int greenValue = Color.green(colorFromBundle);
            final int blueValue = Color.blue(colorFromBundle);

            binding.redSeekBar.setProgress(redValue);
            binding.greenSeekBar.setProgress(greenValue);
            binding.blueSeekBar.setProgress(blueValue);
            onColorChanged(redValue, greenValue, blueValue);
        }

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_REQUEST_KEY, requestKey);
        outState.putInt(KEY_COLOR, color);
        super.onSaveInstanceState(outState);
    }

    private void onColorChanged(final int redValue, final int greenValue, final int blueValue) {
        binding.blueValueTextView.setText(Integer.toString(redValue));
        binding.greenValueTextView.setText(Integer.toString(greenValue));
        binding.blueValueTextView.setText(Integer.toString(blueValue));
        color = Color.argb(255, redValue, greenValue, blueValue);
        binding.colorView.setBackgroundColor(color);
    }

}
