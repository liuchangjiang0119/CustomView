package com.windfall.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.windfall.customview.R;

/**
 * Created by windfall on 16-11-16.
 */

public class ProgressBarView extends View {
    private int paintBold;
    private int linelengeth;
    private int linenumber;
    private int backgroundColor;
    private int beforeColor;
    private int textColor;
    private int max;
    private int progress;

    private int width;
    private int height;

    private Paint backgroundPaint,beforePaint,textPaint;

    public ProgressBarView(Context context) {
        super(context);
    }

    public int getProgress() {
        return progress;
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context,attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.progressbar);
        paintBold = typedArray.getDimensionPixelSize(R.styleable.progressbar_paintBold,10);
        linelengeth = typedArray.getDimensionPixelSize(R.styleable.progressbar_linelength,25);
        linenumber = typedArray.getInteger(R.styleable.progressbar_linenumber,20);
        backgroundColor = typedArray.getColor(R.styleable.progressbar_backgroundColor, Color.GRAY);
        beforeColor = typedArray.getColor(R.styleable.progressbar_beforeColor,Color.YELLOW);
        textColor = typedArray.getColor(R.styleable.progressbar_textColor,Color.BLACK);
        max = typedArray.getInteger(R.styleable.progressbar_max,100);

        typedArray.recycle();

        initPaint();
    }



    public void initPaint(){
        backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStrokeWidth(paintBold);
//        设置结合处样式，Round为圆弧,Miter为锐角，Bevel为直线
        backgroundPaint.setStrokeJoin(Paint.Join.ROUND);
//        设置笔刷样式
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);
//        抗锯齿
        backgroundPaint.setAntiAlias(true);

        beforePaint = new Paint();
        beforePaint.setColor(beforeColor);
        beforePaint.setStrokeWidth(paintBold);
        beforePaint.setAntiAlias(true);
        beforePaint.setStrokeJoin(Paint.Join.ROUND);
        beforePaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getSize(100,widthMeasureSpec);
        height = getSize(100,heightMeasureSpec);

    }

    public int getSize(int defaultSize,int measureSpec){
        int size = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

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
        int r = x-5;
        for (int i = 0;i<linenumber;i++){
            canvas.drawLine(x,y-r,x,y-r+linelengeth,backgroundPaint);
            canvas.rotate(360/linenumber,x,y);
        }
        int count = getProgress()*linenumber/max;
        canvas.drawText(getProgress()*100/max+"%",x,y,textPaint);
        for (;count>0;count--){
            canvas.drawLine(x,y-r,x,y-r+linelengeth,beforePaint);
            canvas.rotate(360/linenumber,x,y);
        }


    }





    public void setProgress(int num){
        this.progress = num;

        invalidate();
    }


}
