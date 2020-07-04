package com.infectdistrack.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.infectdistrack.R;

import static android.view.View.GONE;

public class CommunityWatchActivity extends AppCompatActivity {

    private NumberPicker picker1, picker2, picker3, picker4;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_watch);

        picker1 = findViewById(R.id.numberpicker_main_picker);
        picker1.setMinValue(0);
        picker1.setMaxValue(100);

        picker2 = findViewById(R.id.number_main_picker);
        picker2.setMinValue(0);
        picker2.setMaxValue(100);

        picker3 = findViewById(R.id.main_picker);
        picker3.setMinValue(0);
        picker3.setMaxValue(100);

        picker4 = findViewById(R.id.mber_main_picker);
        picker4.setMinValue(0);
        picker4.setMaxValue(100);

        linearLayout = findViewById(R.id.kiu);
        linearLayout.setVisibility(GONE);

        picker4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                linearLayout.setVisibility(picker4.getValue() == 0 ? GONE : View.VISIBLE);
            }
        });
    }
}
