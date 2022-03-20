package com.yanl.mission02_two;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * author : YanL
 * date : 2019/11/29 16:37
 * email : imyanl.dt@gmail.com
 * description :
 */
public class FingerTrackView extends android.support.v7.widget.AppCompatImageView {
    private Path mPath = new Path();
    private float mPreX,mPreY;
    public boolean flag = false;

    public FingerTrackView(Context context) {
        super(context);
    }

    public FingerTrackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FingerTrackView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){



        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                Log.e("some", "我在画图");
                float endX = (mPreX + event.getX())/2;
                float endY = (mPreY + event.getY())/2;
                mPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY = event.getY();
                invalidate();
                break;
            default:
                break;
        }

//        ViewParent viewParent = getParent();
//        if(viewParent instanceof ViewGroup){
//            ((ViewGroup) viewParent).onTouchEvent(event);
//
//        }
        return super.onTouchEvent(event);
    }


    //事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){

        flag = true;
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(mPath, paint);
    }
}
