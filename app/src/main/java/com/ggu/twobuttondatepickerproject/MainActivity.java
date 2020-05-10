package com.ggu.twobuttondatepickerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ggu.twobuttondatepicker.DateUtils;
import com.ggu.twobuttondatepicker.TwoButtonDatePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TwoButtonDatePicker.OnClickListener {

    private TwoButtonDatePicker defaultPickerView, customPickerView;
    private EditText editMaxDays, editMinDays;
    private TextView defaultContent, customContent;
    private Button defaultBtn, customBtn;
    private Button defaultResetBtn, customResetBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        /** Register the listener for clicking the previous and next buttons of DatePicker. **/
        defaultPickerView.setDatePickerClickListener(this);
        customPickerView.setDatePickerClickListener(this);

        /*
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        customPickerView.setTargetDate(calendar.getTime());
        */

        /** Register DatePicker's date change listener **/
        defaultPickerView.addOnDateChangedListener(date->{
            Toast.makeText(this, "DEFAULT : "+DateUtils.getTargetDate(date, DateUtils.DATE_FORMAT_FULL_SLASH), Toast.LENGTH_SHORT).show();
        });

        customPickerView.addOnDateChangedListener(date->{
            Toast.makeText(this, "CUSTOM : "+DateUtils.getTargetDate(date, DateUtils.DATE_FORMAT_FULL_SLASH), Toast.LENGTH_SHORT).show();
        });


        /** Set Content Text **/
        StringBuilder defaultSb = new StringBuilder();
        StringBuilder customSb = new StringBuilder();
        setContent(defaultContent, defaultSb.append("ADJUST SELECTION : ").append(defaultPickerView.isAdjustSelection()).append("\n").append("ADJUST VISIBILITY : ").append(defaultPickerView.isAdjustVisibility()).append("\n").append("MAX_DAYS : ").append(defaultPickerView.getMaxDays()).append("\n").append("MIN_DAYS : ").append(defaultPickerView.getMinDays()).toString());
        setContent(customContent, customSb.append("ADJUST SELECTION : ").append(customPickerView.isAdjustSelection()).append("\n").append("ADJUST VISIBILITY : ").append(customPickerView.isAdjustVisibility()).append("\n").append("MAX_DAYS : ").append(customPickerView.getMaxDays()).append("\n").append("MIN_DAYS : ").append(customPickerView.getMinDays()).toString());


        /** Customize DatePicker's MIN_DAYS and MAX_DAYS **/
        defaultBtn.setOnClickListener(view->{
            StringBuilder sb = new StringBuilder();
            defaultPickerView.setMaxDays(getEditTextInteger(editMaxDays));
            defaultPickerView.setMinDays(getEditTextInteger(editMinDays));
            defaultPickerView.reset();
            setContent(defaultContent, sb.append("ADJUST SELECTION : ").append(defaultPickerView.isAdjustSelection()).append("\n").append("ADJUST VISIBILITY : ").append(defaultPickerView.isAdjustVisibility()).append("\n").append("MAX_DAYS : ").append(defaultPickerView.getMaxDays()).append("\n").append("MIN_DAYS : ").append(defaultPickerView.getMinDays()).toString());

            Toast.makeText(this, "APPLY DEFAULT PICKER : "+"MAX_DAYS is :"+editMaxDays.getText().toString()+", MIN_DAYS is : "+editMinDays.getText().toString(), Toast.LENGTH_SHORT).show();
        });

        customBtn.setOnClickListener(view->{
            StringBuilder sb = new StringBuilder();
            customPickerView.setMaxDays(getEditTextInteger(editMaxDays));
            customPickerView.setMinDays(getEditTextInteger(editMinDays));
            customPickerView.reset();
            setContent(customContent, sb.append("ADJUST SELECTION : ").append(customPickerView.isAdjustSelection()).append("\n").append("ADJUST VISIBILITY : ").append(customPickerView.isAdjustVisibility()).append("\n").append("MAX_DAYS : ").append(customPickerView.getMaxDays()).append("\n").append("MIN_DAYS : ").append(customPickerView.getMinDays()).toString());

            Toast.makeText(this, "APPLY CUSTOM PICKER : "+"MAX_DAYS is :"+editMaxDays.getText().toString()+", MIN_DAYS is : "+editMinDays.getText().toString(), Toast.LENGTH_SHORT).show();
        });


        /** Reset the current Date to the DatePicker's targetDate **/
        defaultResetBtn.setOnClickListener(view->{
            defaultPickerView.reset();
        });

        customResetBtn.setOnClickListener(view->{
            customPickerView.reset();
        });

    }

    private void init(){
        defaultPickerView = findViewById(R.id.datePickerView_default);
        customPickerView = findViewById(R.id.datePickerView_custom);
        defaultContent = findViewById(R.id.content_default);
        customContent = findViewById(R.id.content_custom);
        editMaxDays = findViewById(R.id.edit_max);
        editMinDays = findViewById(R.id.edit_min);
        defaultBtn = findViewById(R.id.default_btn);
        customBtn = findViewById(R.id.custom_btn);
        defaultResetBtn = findViewById(R.id.default_reset_btn);
        customResetBtn = findViewById(R.id.custom_reset_btn);
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
        else if(view.getText().toString().isEmpty()) return 7;
        else return Integer.parseInt(view.getText().toString());
    }

    private void setContent(TextView textView, String content){
        textView.setText(content);
    }
}
