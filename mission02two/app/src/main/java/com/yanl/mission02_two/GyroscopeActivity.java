package com.yanl.mission02_two;

/**
 * author : YanL
 * date : 2019/11/29 16:04
 * email : imyanl.dt@gmail.com
 * description :
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
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

public class GyroscopeActivity extends AppCompatActivity {
    private GyroscopeObserver gyroscopeObserver;
    private GyroscopeImageView gyroscopeImageView;
    TextView tx1, tx2;
    private FlingView flingView;
    private long mode_id;
    long missionnumber;
    long startTime, runTime;
    File dir, mission2TimeFile;
    String dirname, mission2ExcelName, mission1ExcelName;
    String finger;
    String[] dataTime2 = {"", "", ""};
    String testnumber;
//    Button buttonMission1;
    Button buttonOver;
    Vibrator vibrator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("filepath");
        String angleX = intent.getStringExtra("angleX");
        String angleY = intent.getStringExtra("angleY");
        int imageId = intent.getIntExtra("resourceId", 0);
//        missionnumber = intent.getLongExtra("missionnumber", 0);


        finger = intent.getStringExtra("finger");
        testnumber = intent.getStringExtra("number");

        dirname = "Gyroscope_vs_Drag";
        mission2ExcelName = "Mission02-two.xls";
        dir = new File("/storage/sdcard0/" + dirname);
        mission2TimeFile = new File(dir, mission2ExcelName);


//        Log.e("image", imagePath);
//        Bitmap bm = BitmapFactory.decodeFile(imagePath);




        setContentView(R.layout.gyroscope_imageview);
        flingView = findViewById(R.id.fling);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);




        gyroscopeImageView = findViewById(R.id.gyroscope);
        gyroscopeImageView.setImageResource(imageId);
        gyroscopeImageView.setScaleType(ImageView.ScaleType.CENTER);


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





    }


    public void createMission2Time(){
        dataTime2[0] = testnumber;
        dataTime2[1] = String.valueOf(runTime);
        dataTime2[2] = finger;
    }


    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){

        Context context = getApplicationContext();
        CharSequence textTime = "计时已完成";
        int duration = Toast.LENGTH_SHORT;

        if(keycode == KeyEvent.KEYCODE_BACK){


                    long endTime = System.currentTimeMillis();
                    runTime = endTime - startTime;
                    Log.e("TimeStart", endTime+" ");
                    createMission2Time();
                    try {
                        Workbook timeWorkbook = Workbook.getWorkbook(mission2TimeFile);
                        Sheet sheet = timeWorkbook.getSheet(0);
                        int length = sheet.getRows();
                        WritableWorkbook writableWorkbook = Workbook.createWorkbook(mission2TimeFile, timeWorkbook);
                        WritableSheet writableSheet = writableWorkbook.getSheet(0);
                        for(int i = 0; i < dataTime2.length; i++){
                            Label label = new Label(i, length, dataTime2[i]);
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
