package com.ggu.twobuttondatepickerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ggu.twobuttondatepicker.DateUtils;
import com.ggu.twobuttondatepicker.TwoButtonDatePicker;

public class MainActivity extends AppCompatActivity implements TwoButtonDatePicker.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwoButtonDatePicker defaultPickerView = findViewById(R.id.datePickerView_default);
        TwoButtonDatePicker customPickerView = findViewById(R.id.datePickerView_custom);

        defaultPickerView.setDatePickerClickListener(this);
        customPickerView.setDatePickerClickListener(this);

        defaultPickerView.addOnDateChangedListener(date->{
            Toast.makeText(this, "DEFAULT : "+DateUtils.getTargetDate(date, DateUtils.DATE_FORMAT_FULL_SLASH), Toast.LENGTH_SHORT).show();
        });

        customPickerView.addOnDateChangedListener(date->{
            Toast.makeText(this, "CUSTOM : "+DateUtils.getTargetDate(date, DateUtils.DATE_FORMAT_FULL_SLASH), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void prevButton(View view, boolean lastDay) {
        if(lastDay) Toast.makeText(this, "prevButton : isLastDay", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void nextButton(View view, boolean lastDay) {
        if(lastDay) Toast.makeText(this, "nextButton : isLastDay", Toast.LENGTH_SHORT).show();
    }
}
