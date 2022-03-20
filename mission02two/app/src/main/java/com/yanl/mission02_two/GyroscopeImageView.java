package com.yanl.mission02_two;

/**
 * author : YanL
 * date : 2019/11/29 16:06
 * email : imyanl.dt@gmail.com
 * description :
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Scroller;

public class GyroscopeImageView extends android.support.v7.widget.AppCompatImageView {


    private Path mPath = new Path();
    private float mPreX, mPreY;


    private double ScaleX;
    private double ScaleY;
    private float LenX;
    private float LenY;
    protected double AngleX ;
    protected double AngleY ;
    private float OffsetX;
    private float OffsetY;

    private int width;
    private int height;
    private int drawableWitdth;
    private int drawableHeight;
    int boundaryDistance;


    float dx;
    float dy;
    float lastX;
    float lastY;
    private float startX;
    private float startY;
    public boolean flag = false;
    //DisplayMetrics dm=new DisplayMetrics();
    private Scroller mScroller;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private GestureDetector gestureDetector;
    private float mSpeedX = 0;
    private float mSpeedY = 0;


    //基本的三个构造函数

    public GyroscopeImageView(Context context) {
        super(context);
        init(context);
    }

    public GyroscopeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public GyroscopeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);


    }

    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new
            GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    Log.e("Failing", velocityX + " : " + velocityY);
                    mSpeedX = velocityX;
                    mSpeedY = velocityY;
                    Log.e("xspeed", String.valueOf(mSpeedX));
                    Log.e("xspeed", String.valueOf(mSpeedY));
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
            };


    private void init(Context context) {
        setScaleType(ScaleType.CENTER);
    }


    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(ScaleType.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        if (getDrawable() != null) {
            drawableWitdth = getDrawable().getIntrinsicWidth();
            drawableHeight = getDrawable().getIntrinsicHeight();
            Log.e("Width",String.valueOf(drawableWitdth));
            Log.e("Height",String.valueOf(drawableHeight));

            if(drawableWitdth > drawableHeight){
                boundaryDistance = 1;
            }
            else {
                boundaryDistance = -1;
            }
            Log.e("boundary", String.valueOf(boundaryDistance));

            LenX = Math.abs((drawableWitdth - width) * 0.5f);
            LenY = Math.abs((drawableHeight - height) * 0.5f);
//            Log.e("x",String.valueOf(width));

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //判断是否存在布局或者是否处于ide布局编辑状态
//        if(getDrawable() == null || isInEditMode()){
//            super.onDraw(canvas);
//            return;
//        }


        OffsetX = (float) (LenX * ScaleY);
        OffsetY = (float) (LenY * ScaleX);


        canvas.save();
        canvas.translate(OffsetX, OffsetY);
        super.onDraw(canvas);
        canvas.restore();
    }

    public float getOffsetX() {
        return OffsetX;
    }

    public float getOffsetY() {
        return OffsetY;
    }

    public void setGyroscopeObserver(GyroscopeObserver observer) {
        if (observer != null) {
            observer.addPanoramaImageView(this);
        }
    }

    public void update(double scalex, double scaley) {
        ScaleX = scalex;
        ScaleY = scaley;

        invalidate();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}