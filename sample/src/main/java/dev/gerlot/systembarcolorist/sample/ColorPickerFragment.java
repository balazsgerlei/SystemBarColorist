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

    private ColorPickerFragmentBinding binding;

    private String requestKey;
    private int color;

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
        if (getArguments() != null) {
            requestKey = getArguments().getString(ARG_REQUEST_KEY);
            color = getArguments().getInt(ARG_COLOR);
        }
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            requestKey = getArguments().getString(ARG_REQUEST_KEY);
            color = getArguments().getInt(ARG_COLOR);
        }
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

        final int redValue = Color.red(color);
        binding.redValueTextView.setText(Integer.toString(redValue));
        binding.redSeekBar.setProgress(redValue);
        binding.redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    binding.redValueTextView.setText(Integer.toString(progress));
                    onColorChanged();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        final int greenValue = Color.green(color);
        binding.greenValueTextView.setText(Integer.toString(greenValue));
        binding.greenSeekBar.setProgress(greenValue);
        binding.greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    binding.greenValueTextView.setText(Integer.toString(progress));
                    onColorChanged();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        final int blueValue = Color.blue(color);
        binding.blueValueTextView.setText(Integer.toString(blueValue));
        binding.blueSeekBar.setProgress(blueValue);
        binding.blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    binding.blueValueTextView.setText(Integer.toString(progress));
                    onColorChanged();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void onColorChanged() {
        final int redValue = binding.redSeekBar.getProgress();
        final int greenValue = binding.greenSeekBar.getProgress();
        final int blueValue = binding.blueSeekBar.getProgress();
        color = Color.argb(255, redValue, greenValue, blueValue);
        binding.colorView.setBackgroundColor(color);
    }

}
