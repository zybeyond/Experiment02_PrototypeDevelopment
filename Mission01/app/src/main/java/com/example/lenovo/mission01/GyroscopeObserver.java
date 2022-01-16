package com.example.lenovo.mission01;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.LinkedList;

/**
 * author : YanL
 * date : 2019/11/29 16:05
 * email : imyanl.dt@gmail.com
 * description : 陀螺仪角度监听器
 */
public class GyroscopeObserver implements SensorEventListener {

    private SensorManager mSensormanger;

    private long LastTimeStamp;

    private static final float NS2S = 1.0f / 1000000000.0f;

    double MaxAngleX, angleX;
    double MaxAngleY, angleY;


    private LinkedList<GyroscopeImageView> mViews = new LinkedList<>();

//    private GyroscopeImageView view;


    //添加view到链表中
    void addPanoramaImageView(GyroscopeImageView view) {
        if (view != null && !mViews.contains(view)) {
            mViews.addFirst(view);
        }
    }




    void register(Context context){
        if(mSensormanger == null){
            mSensormanger = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }
        Sensor mSensor = mSensormanger.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensormanger.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);

        //LastTimeStamp = 0;

    }



    void unregister(){
        if(mSensormanger != null){
            mSensormanger.unregisterListener(this);
            mSensormanger = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){



        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            if(LastTimeStamp == 0){
                LastTimeStamp = event.timestamp;
                return;
            }
            if (LastTimeStamp != 0) {

                for (GyroscopeImageView view: mViews){
                    if(view != null)
                    {
                        view.AngleX += event.values[0] * (event.timestamp - LastTimeStamp) * NS2S;
                        view.AngleY += event.values[1] * (event.timestamp - LastTimeStamp) * NS2S;

                        angleX = view.AngleX;
                        angleY = view.AngleY;


                        Log.e("anglex",String.valueOf(view.AngleX));
                        Log.e("angley",String.valueOf(view.AngleY));

                        if (view.AngleX > MaxAngleX) {
                            view.AngleX = MaxAngleX;
                        }
                        if (view.AngleX < -MaxAngleX) {
                            view.AngleX = -MaxAngleX;
                        }
                        if (view.AngleY > MaxAngleY) {
                            view.AngleY = MaxAngleY;
                        }
                        if (view.AngleY < -MaxAngleY) {
                            view.AngleY = -MaxAngleY;
                        }
                        view.update(view.AngleX / MaxAngleX, view.AngleY / MaxAngleY);
//                        Log.e("scale_x",String.valueOf(view.AngleX/MaxAngle));
//                        Log.e("scale_y",String.valueOf(view.AngleY/MaxAngle));
                    }
                }

//                Log.e("LastTimeStamp",String.valueOf(LastTimeStamp));
//                Log.e("TimeStamp",String.valueOf(event.timestamp));
//                Log.e("AngleX",String.valueOf(view.AngleX));


            }
            LastTimeStamp = event.timestamp;
        }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
