package com.windfall.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.TimeZoneFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.windfall.customview.R;

/**
 * Created by windfall on 16-11-16.
 */

public class ClockView extends View {

    private static int width;
    private static int height;
    private int circlewidth;
    private Paint paint,pointPaint,scalePaint,textPaint;
    private final static int SECOND_NUM = 60;
    private final static int SECOND_NUM_BOLD = 12;
    private int scalelength;


    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.clock);
        circlewidth = typedArray.getDimensionPixelSize(R.styleable.clock_circlewidth,10);
        scalelength = typedArray.getDimensionPixelSize(R.styleable.clock_scalelength,25);
        typedArray.recycle();
        initPaint();
    }
    void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(circlewidth);
        paint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(circlewidth);

        scalePaint = new Paint();
        scalePaint.setAntiAlias(true);
        scalePaint.setColor(Color.GRAY);
        scalePaint.setStrokeWidth(5);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.BLACK);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getSize(300,widthMeasureSpec);
        height = getSize(300,widthMeasureSpec);

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
        int x = width/2;
        int y = height/2;
//        减5为了美观
        int r =x-5;


        canvas.drawCircle(x,y,r,paint);
        canvas.drawCircle(x,y,15,pointPaint);
        for (int i=0;i<SECOND_NUM;i++) {
            canvas.drawLine(x, y - r, x, y - r + scalelength, scalePaint);
            canvas.rotate(6,x,y);


        }
        for (int i=0;i<SECOND_NUM_BOLD;i++){
            canvas.drawLine(x,y-r,x,y-r+scalelength*2,paint);
            if (i==0)
            {
                canvas.drawText("12",x,y-r+scalelength*3,textPaint);
            }else {
                canvas.drawText(i + "", x, y - x + scalelength * 4, textPaint);
            }
            canvas.rotate(30,x,y);
        }

        canvas.save();
    }
    public static int getCirleX(){
        return width;
    }

    public static int getCirleY(){
        return height;
    }
}
