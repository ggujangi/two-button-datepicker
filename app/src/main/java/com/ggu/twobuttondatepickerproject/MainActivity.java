package com.ggu.twobuttondatepickerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ggu.twobuttondatepicker.DateUtils;
import com.ggu.twobuttondatepicker.TwoButtonDatePicker;

public class MainActivity extends AppCompatActivity implements TwoButtonDatePicker.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwoButtonDatePicker twoButtonDatePicker = findViewById(R.id.datePickerView);
        TwoButtonDatePicker twoButtonDatePicker2 = findViewById(R.id.datePickerView2);
        twoButtonDatePicker.setDatePickerClickListener(this);
        twoButtonDatePicker2.setDatePickerClickListener(this);

        twoButtonDatePicker.addOnDateChanedListener(date->{
            Log.d("SchduleTest", "date : "+ DateUtils.getTargetDate(date, DateUtils.DATE_FORMAT_FULL_NAME));
        });
        twoButtonDatePicker2.addOnDateChanedListener(date->{
            Log.d("SchduleTest", "date : "+DateUtils.getTargetDate(date, DateUtils.DATE_FORMAT_FULL_NAME));
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
