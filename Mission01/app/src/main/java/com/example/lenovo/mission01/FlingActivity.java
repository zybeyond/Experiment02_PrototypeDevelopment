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
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;


public class FlingActivity extends AppCompatActivity {


    private FlingView flingView;
    private FingerTrackView fingerTrackView;
    Button buttonMission1;
    Button buttonOver;

    Vibrator vibrator;



    String imagePath;
    String testnumber;
    long missionnumber;
    String finger;
    File filename, mission2TimeFile, mission1TimeFile;
    String dirname;
    String name, mission2ExcelName, mission1ExcelName;
    private File dir;
    long startTime, runTime, mission1StartTime;


    String[] dataset = {"", "", "", ""};
    String[] dataTime2 = {"", "", ""};
    String[] dataTime1 = {"", "", ""};


    //    FileOutputStream fileOutputStream = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.fling_imageview);
        buttonMission1 = findViewById(R.id.buttonMission1);
        buttonOver = findViewById(R.id.buttonMission);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        startTime = System.currentTimeMillis();

        Intent intent = getIntent();
//        imagePath = intent.getStringExtra("filepath");
        testnumber = intent.getStringExtra(  "number");
//        missionnumber = intent.getLongExtra("missionnumber", 0);
        int imageId = intent.getIntExtra("resourceId", 0);
        buttonMission1.setVisibility(VISIBLE);
        finger = intent.getStringExtra("finger");
        dirname = "Gyroscope_vs_Drag";
        name = "GyrosVsDrag.xls";
        mission2ExcelName = "Mission2Time.xls";
        mission1ExcelName = "Mission01Horizontal.xls";
        dir = new File("/storage/sdcard0/" + dirname);

        filename = new File(dir, name);
        mission2TimeFile = new File(dir, mission2ExcelName);
        mission1TimeFile = new File(dir, mission1ExcelName);

//        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        flingView = findViewById(R.id.fling);
        flingView.setImageResource(imageId);
        flingView.setScaleType(ImageView.ScaleType.CENTER);
        fingerTrackView = findViewById(R.id.finger);

        buttonMission1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                buttonMission1.setVisibility(INVISIBLE);
                mission1StartTime = System.currentTimeMillis();
            }
        });

        //savebitmap();
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

    public void createDataSet() {

        dataset[0] = testnumber;
        Log.e("任务名", missionnumber + "");
        if (missionnumber == 0) {
            dataset[1] = "1";
        } else if (missionnumber == 1)
            dataset[1] = "2";
        else if (missionnumber == 2)
            dataset[1] = "3";

        Log.e("finger", String.valueOf(finger));
        if (Integer.parseInt(finger) == 0)
            dataset[2] = "thumb";
        else if (Integer.parseInt(finger) == 1)
            dataset[2] = "index";
        else if (Integer.parseInt(finger) == 2)
            dataset[2] = "null";

        dataset[3] = String.valueOf(flingView.count);

        //return dataset;
    }

//    @Override
//    public void onBackPressed(){
//        super.onBackPressed();
//        finish();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Context context = getApplicationContext();
        CharSequence textTime = "计时已完成";
        int duration = Toast.LENGTH_SHORT;

        CharSequence text1 = "图片已保存";   //提示实验完成
        CharSequence text2 = "数据已保存";   //提示实验完成

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {





            try {


                fingerTrackView.setDrawingCacheEnabled(true);
                fingerTrackView.buildDrawingCache();

                final Bitmap bitmapCache = Bitmap.createBitmap(fingerTrackView.getDrawingCache());

                Bitmap bitmap;
                if (bitmapCache != null) {
                    bitmap = Bitmap.createBitmap(bitmapCache);
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "finger", "description");
                    fingerTrackView.setDrawingCacheEnabled(false);
                    Toast toast = Toast.makeText(context, text1, duration);
                    toast.show();
                    Log.e("图片", "保存图片");
                } else
                    bitmap = null;


                Log.e("存储数据", "保存数据");

                createDataSet();
                //Workbook wb = Workbook.getWorkbook(filename);
                //WorkbookSettings ws = new WorkbookSettings();
                Workbook workbook = Workbook.getWorkbook(filename);
                Log.e("filename", filename+" ");
                Sheet sheet = workbook.getSheet(0);
                int length = sheet.getRows();
                Log.e("length", dataset[0]);
                WritableWorkbook writebook = Workbook.createWorkbook(filename, workbook);
                WritableSheet w_sheet = writebook.getSheet(0);

                for (int i = 0; i < dataset.length; i++) {
                    Label label = new Label(i, length, dataset[i]);
                    w_sheet.addCell(label);
                }
                writebook.write();
                writebook.close();
                Toast toast = Toast.makeText(context, text2, duration);
                toast.show();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        if(keyCode == KeyEvent.KEYCODE_BACK ){

            if(missionnumber == 0){
                if(mission1StartTime != 0){
                    long endTime = System.currentTimeMillis();
                    runTime = endTime - mission1StartTime;
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
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //Log.e("runtime", runTime+ " ");
                    Toast toast = Toast.makeText(context, textTime, duration);
                    toast.show();
                }


            }
//            if(missionnumber == 1){
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
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                //Log.e("runtime", runTime+ " ");
//                Toast toast = Toast.makeText(context, textTime, duration);
//                toast.show();
//            }
            //DO SOMETHING
            this.finish();
        }
        return super.onKeyUp(keyCode, event);
    }
}