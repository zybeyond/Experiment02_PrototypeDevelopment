package com.yanl.mission02_one;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * author : YanL
 * date : 2019/11/29 16:38
 * email : imyanl.dt@gmail.com
 * description :
 */
public class MyViewGroup extends RelativeLayout {

    private Path mPath = new Path();
    private float mPreX,mPreY;
//    public boolean flag = false;

    public MyViewGroup(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
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
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final boolean intercept = super.onInterceptTouchEvent(ev);

        // getChildCount() - 1, because `RecyclerView` is the last child
        for (int i = 0, size = getChildCount() - 1; i < size; i++) {
            View v = getChildAt(i);
            v.dispatchTouchEvent(MotionEvent.obtain(ev));
        }
        return intercept;
    }
}
