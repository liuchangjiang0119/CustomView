package com.windfall.customview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;

import android.os.Build;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.windfall.customview.ClockActivity;
import com.windfall.customview.R;

import java.util.Calendar;


/**
 * Created by windfall on 16-11-16.
 */

public class PointerView extends View {
    private int width,height;


    private int scalelength;
    private int hour,minute,second;
    private float hourDegree,minDegree,secDegree;
    Paint secondPaint,minPaint,hourPaint;

    public PointerView(Context context) {
        super(context);
    }

    public PointerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.clock);
        scalelength = typedArray.getDimensionPixelSize(R.styleable.clock_scalelength,25);

        typedArray.recycle();
        initPaint();
    }

    void initPaint(){
        secondPaint = new Paint();
        secondPaint.setColor(Color.RED);
        secondPaint.setAntiAlias(true);
        secondPaint.setStrokeWidth(5);

        minPaint = new Paint();
        minPaint.setColor(Color.GRAY);
        minPaint.setStrokeWidth(10);
        minPaint.setAntiAlias(true);

        hourPaint = new Paint();
        hourPaint.setColor(Color.BLACK);
        hourPaint.setStrokeWidth(15);
        hourPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getSize(300,widthMeasureSpec);
        height = getSize(300,heightMeasureSpec);
    }
    private int getSize(int defaultSize,int measureSpc){
        int size = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpc);
        int specSize = MeasureSpec.getSize(measureSpc);
        switch (specMode){
            case MeasureSpec.UNSPECIFIED:
                size = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                size = specSize;
                break;
            case MeasureSpec.EXACTLY:
                size = specSize;
                break;
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = ClockView.getCirleX()/2;
        int y = ClockView.getCirleY()/2;

        int r= x-5;
        initTime();
        if (hour>12) hour = hour-12;
        hourDegree = hour*30+minute/2;
        minDegree = minute*6+second/10;
        secDegree = second*6;

        canvas.save();
        canvas.rotate(secDegree,x,y);
        canvas.drawLine(x,y-r+scalelength,x,y,secondPaint);

        canvas.restore();
        canvas.save();
        canvas.rotate(minDegree,x,y);
        canvas.drawLine(x,y-r+scalelength*4,x,y,minPaint);
        canvas.restore();


        canvas.rotate(hourDegree,x,y);
        canvas.drawLine(x,y-r+scalelength*6,x,y,hourPaint);
        postInvalidateDelayed(1000);
    }


    void initTime(){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        second = c.get(Calendar.SECOND);

    }
}
