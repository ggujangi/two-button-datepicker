package com.ggu.twobuttondatepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;

import static com.ggu.twobuttondatepicker.DateUtils.DATE_FORMAT_FULL_SLASH;


public class TwoButtonDatePicker extends LinearLayout implements View.OnClickListener {

    public interface OnClickListener{
        void prevButton(View view, boolean lastDay);
        void nextButton(View view, boolean lastDay);
    }

    public interface OnDateChangedListener{
        void onDateChanged(Date date);
    }

    private final int DAYS_ONE_WEEK = 7;
    private final int DAYS_ONE_DAY = 1;

    private OnClickListener clickListener;
    private OnDateChangedListener changedListener;

    public void setDatePickerClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void addOnDateChanedListener(OnDateChangedListener changedListener){
        this.changedListener = changedListener;
    }

    int datePickerLayoutId = R.layout.view_date_picker;

    private int maxDays = DAYS_ONE_WEEK;
    private int minDays = (-1)*DAYS_ONE_WEEK;
    private int distance = DAYS_ONE_DAY;
    private String dateFormat = DATE_FORMAT_FULL_SLASH;

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    public void setMinDays(int minDays) {
        if(minDays>=0) this.minDays = (-1)*minDays;
        else this.minDays = minDays;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    private Date targetDate = Calendar.getInstance().getTime();
    private Date diffDate = targetDate;

    private TextView dateTextView;
    private ImageView nextBtnView, prevBtnView;

    public TwoButtonDatePicker(Context context) {
        this(context, null);
    }

    public TwoButtonDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        init(context);
    }

    private void getAttrs(Context context, AttributeSet attrs){
        if(attrs==null) return;

        TypedArray a =  context.getTheme().obtainStyledAttributes(attrs, R.styleable.TwoButtonDatePicker, 0, 0);

        try{
            datePickerLayoutId = a.getResourceId(R.styleable.TwoButtonDatePicker_ggu_date_layout_id, datePickerLayoutId);
            maxDays = a.getInteger(R.styleable.TwoButtonDatePicker_ggu_date_max_days, maxDays);
            minDays = a.getInteger(R.styleable.TwoButtonDatePicker_ggu_date_min_days, minDays);
            if(minDays>=0) minDays *= -1;
            distance = a.getInteger(R.styleable.TwoButtonDatePicker_ggu_date_distance, distance);
            dateFormat = a.getString(R.styleable.TwoButtonDatePicker_ggu_date_format);
            if(dateFormat==null) dateFormat = DATE_FORMAT_FULL_SLASH;
        }finally {
            a.recycle();
        }
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(datePickerLayoutId, this);

        dateTextView = findViewById(R.id.ggu_date_text);
        nextBtnView = findViewById(R.id.ggu_next_btn);
        prevBtnView = findViewById(R.id.ggu_prev_btn);

        nextBtnView.setOnClickListener(this);
        prevBtnView.setOnClickListener(this);
        setSelectedButton(prevBtnView, !isLastDay(minDays, true));
        setSelectedButton(nextBtnView, !isLastDay(maxDays, false));

        dateTextView.setText(DateUtils.getTargetDate(targetDate, dateFormat));
    }


    @Override
    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        if(view.getId() == R.id.ggu_prev_btn){
            if(clickListener!=null) clickListener.prevButton(view, isLastDay(minDays, true));

            if(!isLastDay(minDays, true)){
                calendar.add(Calendar.DAY_OF_MONTH, (-1)*distance);
                targetDate= calendar.getTime();
                if(changedListener!=null) changedListener.onDateChanged(targetDate);
            }
        }
        else if(view.getId() == R.id.ggu_next_btn){
            if(clickListener!=null) clickListener.nextButton(view, isLastDay(maxDays, false));

            if(!isLastDay(maxDays, false)) {
                calendar.add(Calendar.DAY_OF_MONTH, distance);
                targetDate= calendar.getTime();
                if(changedListener!=null) changedListener.onDateChanged(targetDate);
            }
        }

        /** View Update **/
        if(dateTextView!=null) dateTextView.setText(DateUtils.getTargetDate(targetDate, dateFormat));

        setSelectedButton(prevBtnView, !isLastDay(minDays, true));
        setSelectedButton(nextBtnView, !isLastDay(maxDays, false));
    }

    private boolean isLastDay(int limit, boolean minDay){
        long diffDay = (targetDate.getTime()-diffDate.getTime())/(24*60*60*1000);
        if(minDay) return diffDay<=limit;
        else return diffDay>=limit;
    }


    private void setSelectedButton(View view, boolean isSelected){
        if(view == null) return;
        view.setSelected(isSelected);
    }

    private void setVisibilityButton(View view, boolean isVisible){
        if(view == null) return;
        view.setVisibility(isVisible?VISIBLE:INVISIBLE);
    }
}
