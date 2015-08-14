package com.doouya.mua.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.example.sky.myapplication.R;


/**
 * Created by sky on 2015/8/14.
 */
public class DrawableButton extends View {

    private ColorStateList colorStateList;
    Runnable douClickCheck = new Runnable() {
        public void run() {
            waitingForDouClick = false;
        }
    };
    private OnDouClickListener douClickListener;
//    private int mAlign;
    private Drawable mDrawableBackup;
    private int mDrawablePadding = 0;
    private Drawable mDrawableTop;
    private int mFontColor;
    private int mFontSize;
    private String mText;
    private int mTextHeight = 0;
    private Paint mTextPaint;
    private int mTextWidth = 0;
    private boolean waitingForDouClick = false;

    public DrawableButton(Context context) {
        this(context, null);
    }

    public DrawableButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableButton);
        mText = a.getString(R.styleable.DrawableButton_text);
        mFontColor = a.getColor(R.styleable.DrawableButton_textColor, Color.parseColor("#999999"));
        colorStateList = a.getColorStateList(R.styleable.DrawableButton_textColor);
        mFontSize = a.getDimensionPixelSize(R.styleable.DrawableButton_textSize, 30);
        mDrawablePadding = a.getDimensionPixelOffset(R.styleable.DrawableButton_drawable_padding, 10);
        mDrawableTop = a.getDrawable(R.styleable.DrawableButton_drawable_top);
        a.recycle();

        mTextPaint = new Paint();
        mTextPaint.setTextSize(mFontSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mFontColor);
        setText(mText);
        setClickable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = canvas.getHeight();
        int width = canvas.getWidth();

        int drawableHeight = 0;
        int drawableWidth = 0;

        if(mDrawableTop != null){
            drawableHeight = height - mDrawablePadding - mTextHeight - getPaddingTop() - getPaddingBottom();
            drawableWidth = mDrawableTop.getIntrinsicWidth() * drawableHeight / mDrawableTop.getIntrinsicHeight();
            mDrawableTop.setBounds(0,0,drawableWidth,drawableHeight);
            canvas.save();
            canvas.translate((width - drawableWidth) / 2, getPaddingTop());
            mDrawableTop.draw(canvas);
            canvas.restore();
        }
        canvas.drawText(this.mText, (width - this.mTextWidth) / 2, getPaddingTop() + drawableHeight + this.mDrawablePadding + this.mTextHeight + (this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0F, this.mTextPaint);

    }

    @Override
    public boolean performClick() {
        if (this.waitingForDouClick) {
            performDouClick();
            this.waitingForDouClick = false;
            removeCallbacks(this.douClickCheck);
        }
        this.waitingForDouClick = true;
        postDelayed(this.douClickCheck, ViewConfiguration.getDoubleTapTimeout());
        return super.performClick();
    }

    public void performDouClick() {
        if (this.douClickListener != null) {
            this.douClickListener.OnDouClick(this);
        }
    }

    @Override
    public void refreshDrawableState() {
        super.refreshDrawableState();
        if (this.mDrawableTop != null) {
            this.mDrawableTop.setState(getDrawableState());
        }
        if (this.colorStateList != null) {
            int i = this.colorStateList.getColorForState(getDrawableState(), 0);
            this.mTextPaint.setColor(i);
        }
    }

    public void resetDrawable() {
        if (this.mDrawableBackup != null) {
            this.mDrawableTop = this.mDrawableBackup;
            this.mDrawableBackup = null;
        }
    }


    //设置图片
    public void setDrawableTop(Drawable drawable) {
        if (this.mDrawableBackup == null) {
            this.mDrawableBackup = this.mDrawableTop;
            this.mDrawableTop = drawable;
        }
    }

    //设置字体
    public void setText(String text) {
        mText = (text == null) ? "" : text;
        Rect rect = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), rect);
        mTextHeight = rect.height();
        mTextWidth = rect.width();
        invalidate();
    }

    //设置字体颜色
    public void setTextColor(int color) {
        mTextPaint.setColor(color);
        invalidate();
    }

    //设置双击监听
    public void setOnDouClickListener(OnDouClickListener listener) {
        this.douClickListener = listener;
    }


}
