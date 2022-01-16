package com.example.lenovo.mission01;

/**
 * author : YanL
 * date : 2019/11/29 16:04
 * email : imyanl.dt@gmail.com
 * description :
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class GyroscopeActivity extends  AppCompatActivity{
    private GyroscopeObserver gyroscopeObserver;
    private GyroscopeImageView gyroscopeImageView;
    TextView tx1, tx2;
    private FlingView flingView;
    private long mode_id;
    long missionnumber;
    long startTime, runTime, mission1StartTime = 0;
    File dir, mission2TimeFile, mission1TimeFile;
    String dirname, mission2ExcelName, mission1ExcelName;
    String finger;
    String[] dataTime2 = {"", "", ""};
    String[] dataTime1 = {"", "", ""};
    String testnumber;
    Button buttonMission1;
    Button buttonOver;
    Vibrator vibrator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("filepath");
        String angleX = intent.getStringExtra("angleX");
        String angleY = intent.getStringExtra("angleY");
        int imageId = intent.getIntExtra("resourceId", 0);
//        missionnumber = intent.getLongExtra("missionnumber", 0);


        finger = intent.getStringExtra("finger");
        testnumber = intent.getStringExtra("number");

        dirname = "Gyroscope_vs_Drag";
        mission2ExcelName = "Mission2Time.xls";
        mission1ExcelName = "Mission01Horizontal.xls";
        dir = new File("/storage/sdcard0/" + dirname);
        mission2TimeFile = new File(dir, mission2ExcelName);
        mission1TimeFile = new File(dir, mission1ExcelName);


        //Log.e("image", imagePath);
        Bitmap bm = BitmapFactory.decodeFile(imagePath);




        setContentView(R.layout.gyroscope_imageview);
        flingView = findViewById(R.id.fling);
        buttonMission1 = findViewById(R.id.buttonMission1G);
        buttonOver = findViewById(R.id.buttonMission);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        buttonMission1.setVisibility(VISIBLE);

        startTime = System.currentTimeMillis();

        gyroscopeImageView = findViewById(R.id.gyroscope);
        gyroscopeImageView.setImageResource(imageId);
        gyroscopeImageView.setScaleType(ImageView.ScaleType.CENTER);

//        int distance = gyroscopeImageView.getDrawable();
//        Log.e("Gettop", distance + " ");
        int[] windowsLocation = new int[2];
        gyroscopeImageView.getLocationInWindow(windowsLocation);
        Log.e("Windows", windowsLocation[0] + windowsLocation[1] + " ");
        int[] screenLocation = new int[2];
        gyroscopeImageView.getLocationOnScreen(screenLocation);
        Log.e("Screen", screenLocation[0] + screenLocation[1] + " ");

        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.MaxAngleX = Math.PI / Double.valueOf(angleX);
        gyroscopeObserver.MaxAngleY = Math.PI / Double.valueOf(angleY);
        Log.e("AngleX", gyroscopeObserver.MaxAngleX +" ");
        gyroscopeImageView.setGyroscopeObserver(gyroscopeObserver);


//        tx1.setText(String.valueOf(gyroscopeObserver.angleX));
//        tx2.setText(String.valueOf(gyroscopeObserver.angleY));

//        if(flingView.isShow){
//            buttonOver.setVisibility(VISIBLE);
//        }


        buttonMission1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibrator.vibrate(100);
                /**
                 *
                 */
                buttonMission1.setVisibility(INVISIBLE);
                mission1StartTime = System.currentTimeMillis();
            }
        });


    }

    public void createMission1Time(){
        dataTime1[0] = testnumber;
        dataTime1[1] = String.valueOf(runTime);
        dataTime1[2] = finger;
//        if (Integer.parseInt(finger) == 0)
//            dataTime1[2] = "thumb";
//        else if (Integer.parseInt(finger) == 1)
//            dataTime1[2] = "index";
//        else if (Integer.parseInt(finger) == 2)
//            dataTime1[2] = "null";
    }

//    public void createMission2Time(){
//        dataTime2[0] = testnumber;
//        dataTime2[1] = String.valueOf(runTime);
//        if (Integer.parseInt(finger) == 0)
//            dataTime2[2] = "thumb";
//        else if (Integer.parseInt(finger) == 1)
//            dataTime2[2] = "index";
//        else if (Integer.parseInt(finger) == 2)
//            dataTime2[2] = "null";
//    }


    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){

        Context context = getApplicationContext();
        CharSequence textTime = "计时已完成";
        int duration = Toast.LENGTH_SHORT;

        if(keycode == KeyEvent.KEYCODE_BACK){
                if(mission1StartTime != 0){

                    Log.e("TimeStart", mission1StartTime+" ");
                    long endTime = System.currentTimeMillis();
                    runTime = endTime - mission1StartTime;
                    Log.e("TimeStart", endTime+" ");
                    createMission1Time();

                    try {
                        Workbook timeWorkbook = Workbook.getWorkbook(mission1TimeFile);
                        Sheet sheet = timeWorkbook.getSheet(0);
                        int length = sheet.getRows();
                        WritableWorkbook writableWorkbook = Workbook.createWorkbook(mission1TimeFile, timeWorkbook);
                        WritableSheet writableSheet = writableWorkbook.getSheet(0);
                        for(int i = 0; i < dataTime1.length; i++){
                            Label label = new Label(i, length, dataTime1[i]);
                            writableSheet.addCell(label);
                        }
                        writableWorkbook.write();
                        writableWorkbook.close();
                        //Log.e("runtime", runTime+ " ");
                        Toast toast = Toast.makeText(context, textTime, duration);
                        toast.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }



//            else if(missionnumber == 1){
//                long endTime = System.currentTimeMillis();
//                runTime = endTime - startTime;
//                createMission2Time();
//                try {
//                    Workbook timeWorkbook = Workbook.getWorkbook(mission2TimeFile);
//                    Sheet sheet = timeWorkbook.getSheet(0);
//                    int length = sheet.getRows();
//                    WritableWorkbook writableWorkbook = Workbook.createWorkbook(mission2TimeFile, timeWorkbook);
//                    WritableSheet writableSheet = writableWorkbook.getSheet(0);
//                    for(int i = 0; i < dataTime2.length; i++){
//                        Label label = new Label(i, length, dataTime2[i]);
//                        writableSheet.addCell(label);
//                    }
//                    writableWorkbook.write();
//                    writableWorkbook.close();
//                    Log.e("runtime", runTime+ " ");
//                    Toast toast = Toast.makeText(context, textTime, duration);
//                    toast.show();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }

            this.finish();
        }

        return super.onKeyUp(keycode, event);
    }

    @Override
    protected void onResume(){
        super.onResume();
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        gyroscopeObserver.unregister();
    }
}
