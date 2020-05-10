package com.ggu.twobuttondatepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


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

    public void addOnDateChangedListener(OnDateChangedListener changedListener){
        this.changedListener = changedListener;
    }

    /** Custom Layout ID **/
    int datePickerLayoutId = R.layout.view_date_picker;

    /** MAX_DAYS of DatePicker. Default is 7 **/
    private int maxDays = DAYS_ONE_WEEK;

    /** MIN_DAYS of DatePicker. Default is 7 **/
    private int minDays = (-1)*DAYS_ONE_WEEK;

    /** Interval Date of DatePicker. Default is 1 **/
    private int distance = DAYS_ONE_DAY;

    /** Date format of DatePicker. Default is YYYY-MM-dd **/
    private String dateFormat = DATE_FORMAT_FULL_SLASH;

    /** View Adjust Method **/
    private boolean adjustVisibility, adjustSelection;


    /**
     * Setting MAX_DAYS of DatePicker
     **/
    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }


    /**
     * Get MAX_DAYS
     **/
    public int getMaxDays() {
        return maxDays;
    }


    /**
     * Setting MIN_DAYS of DatePicker
     * If a number greater than 0 is set, it is made negative.
     **/
    public void setMinDays(int minDays) {
        if(minDays>=0) this.minDays = (-1)*minDays;
        else this.minDays = minDays;
    }


    /**
     * Get MIN_DAYS
     **/
    public int getMinDays() {
        return minDays;
    }


    /**
     * Setting start date
     **/
    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
        this.diffDate = this.targetDate;

        if(dateTextView!=null) dateTextView.setText(DateUtils.getTargetDate(targetDate, dateFormat));
    }


    /**
     * Setting date interval
     **/
    public void setDistance(int distance) {
        this.distance = distance;
    }


    /**
     * Setting date format
     **/
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;

        if(dateTextView!=null) dateTextView.setText(DateUtils.getTargetDate(targetDate, dateFormat));
    }


    /**
     * Setting the View Adjustment Method when LastDay is reached
     *
     * {@adjustSelection} adjusts the selection state of the view
     * {@adjustVisibility} adjusts the visibility of the view
     **/
    public void setAdjustSelection(boolean adjustSelection) {
        this.adjustSelection = adjustSelection;
    }

    public void setAdjustVisibility(boolean adjustVisibility) {
        this.adjustVisibility = adjustVisibility;
    }


    public boolean isAdjustSelection() {
        return adjustSelection;
    }


    public boolean isAdjustVisibility() {
        return adjustVisibility;
    }


    /**
     * Reset Current Date
     **/
    public void reset(){
        targetDate = diffDate;
        adjustButtonView(prevBtnView, isLastDay(minDays, true));
        adjustButtonView(nextBtnView, isLastDay(maxDays, false));
        if(dateTextView!=null) dateTextView.setText(DateUtils.getTargetDate(targetDate, dateFormat));

        Toast.makeText(getContext(), "reset...", Toast.LENGTH_SHORT).show();
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

            adjustVisibility = a.getBoolean(R.styleable.TwoButtonDatePicker_ggu_date_adjustVisibility, false);
            adjustSelection = a.getBoolean(R.styleable.TwoButtonDatePicker_ggu_date_adjustSelection, false);
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

        adjustButtonView(prevBtnView, isLastDay(minDays, true));
        adjustButtonView(nextBtnView, isLastDay(maxDays, false));

        if(dateTextView!=null) dateTextView.setText(DateUtils.getTargetDate(targetDate, dateFormat));
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

        adjustButtonView(prevBtnView, isLastDay(minDays, true));
        adjustButtonView(nextBtnView, isLastDay(maxDays, false));
    }

    private boolean isLastDay(int limit, boolean minDay){
        long diffDay = (targetDate.getTime()-diffDate.getTime())/(24*60*60*1000);
        if(minDay) return diffDay<=limit;
        else return diffDay>=limit;
    }


    /**
     * Adjust Button View Visibility or Selection
     *
     * if {@adjustVisibility} is true, Adjust the visibility of the view when the current date reaches the last day
     * and if {@adjustSelection} is true, Adjust the selection of the view when the current date reaches the last day
     *
     * {@adjustVisibility} has higher priority than {@adjustSelection}
     **/
    private void adjustButtonView(View view, boolean isLastDay){
        if(view == null) return;
        if(adjustSelection){
            view.setSelected(isLastDay);
        }
        Log.d("ButtonTest", "adjustButtonView : "+adjustSelection+", "+isLastDay);

        if(adjustVisibility){
            view.setVisibility(isLastDay?INVISIBLE:VISIBLE);
        }
    }
}
