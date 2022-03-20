package com.yanl.mission02_two;

/**
 * author : YanL
 * date : 2019/11/29 16:36
 * email : imyanl.dt@gmail.com
 * description :
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.OverScroller;

public class FlingView extends android.support.v7.widget.AppCompatImageView {
    Paint paint = new Paint();
    OverScroller mScroller;
    private VelocityTracker mVelocityTracker = null;
    public static final int SNAP_VELOCITY = 250;
    private boolean isScrolling;
    int count;
    boolean isShow;


    public FlingView(Context context){
        super(context);
        init();
    }

    public FlingView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public FlingView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }
    private void init(){
        setScaleType(ScaleType.CENTER);
        mScroller = new OverScroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    private int width;
    private int height;
    private int drawableWitdth;
    private int drawableHeight;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        if (getDrawable() != null){
            drawableWitdth = getDrawable().getIntrinsicWidth();
            drawableHeight = getDrawable().getIntrinsicHeight();

        }
    }



    /**
     *
     *
     */


    public void smoothScrollBy(int dx, int dy){


        if(mScroller.getFinalX() + dx > (drawableWitdth - width) / 2){
            isShow = true;

            dx = (drawableWitdth - width) / 2 - mScroller.getFinalX();
            //dy = drawableHeight - mScroller.getFinalY();
        }else if(mScroller.getFinalX() + dx < -(drawableWitdth - width) / 2){
            isShow = true;
            dx = -1 * (mScroller.getFinalX()+(drawableWitdth - width) / 2);
        }

        if(drawableHeight > height){

            if(mScroller.getFinalY() + dy > (drawableHeight - height) / 2){
                isShow = true;
                dy = (drawableHeight - height) / 2 - mScroller.getFinalY();
            }
            else if(mScroller.getFinalY() + dy < -(drawableHeight - height) / 2){
                isShow = true;
                dy = (drawableHeight - height) / 2 + mScroller.getFinalY();
            }
        }
        else if(drawableHeight < height){
            dy = 0;

            //dy = -1 * mScroller.getFinalY();
        }

        Log.e("finalx", mScroller.getFinalY()+" " + dy + " "+ drawableHeight +" " + height);
        mScroller.startScroll(mScroller.getFinalX(),mScroller.getFinalY(), dx, dy,250);
        isScrolling = true;
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    /**
     * 根据瞬时速度，让画布滑行
     * @param velocityX X轴速度，有正负方向，正数画布左移
     * @param velocityY
     */
    public void fling( int velocityX, int velocityY){

        isScrolling = true;
        Log.e("scroller", mScroller.getFinalX()+" " +velocityX+" "+ velocityY);
        //最后两个是参数是允许的超过边界值的距离

        mScroller.fling(mScroller.getFinalX(), mScroller.getFinalY(), velocityX/3, -velocityY/3, -(drawableWitdth - width) / 2,(drawableWitdth - width) / 2,-(drawableHeight - height) / 2,(drawableHeight - height) / 2);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    /**
     * 当draw()调用时此方法被调用，用来将内容滚动到Scroller的当前坐标
     */
    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }



    @Override
    protected void onDraw(Canvas canvas) {
//        判断是否存在布局或者是否处于ide布局编辑状态
//        if(getDrawable() == null || isInEditMode()){
//            super.onDraw(canvas);
//            return;
//        }
        super.onDraw(canvas);
    }


    int lastX;
    int lastY;
    int currentX;
    int currentY;
    int distanceX;
    int distanceY;
    private float OffsetX;
    private float OffsetY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

//                    if(!mScroller.isFinished()){
//                        mScroller.abortAnimation();
//                    }
                isScrolling = false;
                count++;

                lastX= (int) event.getX();
                lastY =(int) event.getY();
                Log.e("distance", lastX+" ");
                break;
            case MotionEvent.ACTION_MOVE:


                //计算出两次动作间的滑动距离
                currentX = (int) event.getX();
                currentY = (int) event.getY();
                distanceX = currentX - lastX;
                distanceY = currentY - lastY;
                distanceX= distanceX*-1;
                distanceY = -1 * distanceY;
                lastX = currentX;
                lastY = currentY;
                smoothScrollBy(distanceX,distanceY);
                Log.e("distance", distanceY+" ");
                Log.e("distance", distanceX + lastX+ "+" + currentX);
                Log.e("distance", distanceY + lastY+ "+" + currentY);

                break;
            case MotionEvent.ACTION_UP:
                isShow = false;
                //根据触摸位置计算每像素的移动速率。
                //A value of 1 provides pixels per millisecond, 1000 provides pixels per second, etc.
                mVelocityTracker.computeCurrentVelocity(1000);
                //计算速率
                int velocityX = (int) mVelocityTracker.getXVelocity()*(-1) ;
                int velocityY = (int) mVelocityTracker.getYVelocity();

                //计算出两次动作间的滑动距离
                currentX = (int) event.getX();
                currentY = (int) event.getY();
                distanceX = currentX - lastX;
                distanceY = currentY - lastY;
                distanceX = distanceX * -1;
                distanceY = -1 * distanceY;
                lastX = currentX;
                lastY = currentY;
                //如果速率大于最小速率要求，执行滑行，否则拖动到位置
                if(Math.abs(velocityX)>SNAP_VELOCITY || Math.abs(velocityY) > SNAP_VELOCITY){
//                    if(!mScroller.isFinished()){
//                        mScroller.abortAnimation();
//                    }
                    fling(velocityX, velocityY);
                }else{
                    smoothScrollBy(distanceX,distanceY);
                }
//
                break;
            case MotionEvent.ACTION_CANCEL:
                if(mVelocityTracker != null){
                    mVelocityTracker.recycle();
                    mVelocityTracker=null;
                }
        }
        return true;
    }





}
