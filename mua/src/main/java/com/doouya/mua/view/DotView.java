package com.doouya.mua.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.sky.myapplication.R;

/**
 * Created by sky on 2015/8/14.
 */
public class DotView extends View {

    private Paint mPaint;

    public DotView(Context context) {
        this(context,null);
    }

    public DotView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(context.getResources().getColor(R.color.main_color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int radius = (width > height ? height : width)/2;
        canvas.drawCircle(width/2,height/2,radius,mPaint);
    }
}
