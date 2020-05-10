package com.ggu.twobuttondatepickerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        EditText editMaxDays = findViewById(R.id.edit_max);
        EditText editMinDays = findViewById(R.id.edit_min);
        Button defaultBtn = findViewById(R.id.default_btn);
        Button customBtn = findViewById(R.id.custom_btn);

        defaultBtn.setOnClickListener(view->{
            defaultPickerView.setMaxDays(getEditTextInteger(editMaxDays));
            defaultPickerView.setMinDays(getEditTextInteger(editMinDays));
            Toast.makeText(this, "APPLY DEFAULT PICKER : "+"MAX_DAYS is :"+editMaxDays.getText().toString()+", MIN_DAYS is : "+editMinDays.getText().toString(), Toast.LENGTH_SHORT).show();
        });

        customBtn.setOnClickListener(view->{
            customPickerView.setMaxDays(getEditTextInteger(editMaxDays));
            customPickerView.setMinDays(getEditTextInteger(editMinDays));
            Toast.makeText(this, "APPLY CUSTOM PICKER : "+"MAX_DAYS is :"+editMaxDays.getText().toString()+", MIN_DAYS is : "+editMinDays.getText().toString(), Toast.LENGTH_SHORT).show();
        });

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

    private int getEditTextInteger(EditText view){
        if(view.getText() == null) return 7;
        else return Integer.parseInt(view.getText().toString());
    }
}
