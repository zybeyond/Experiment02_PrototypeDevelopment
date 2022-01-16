package com.example.lenovo.mission01_vertical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btn1 = findViewById(R.id.imageButton);
        ImageButton btn2 = findViewById(R.id.imageButton2);
        ImageButton btn3 = findViewById(R.id.imageButton3);
        ImageButton btn4 = findViewById(R.id.imageButton4);
        ImageButton btn5 = findViewById(R.id.imageButton5);
        ImageButton btn6 = findViewById(R.id.imageButton6);
        ImageButton btn7 = findViewById(R.id.imageButton7);
        ImageButton btn8 = findViewById(R.id.imageButton8);
        ImageButton btn9 = findViewById(R.id.imageButton9);
        final EditText editTextX = findViewById(R.id.editText2);
        final EditText editTextY = findViewById(R.id.editText3);
        final TextView tv = findViewById(R.id.editText);
        int imageCount = 9;
        final int[] imageIds;
        imageIds = new int[imageCount];
        for (int i = 0; i < imageCount; i++) {
            int imageResourceId = getResources().getIdentifier("s" + (i + 1), "mipmap", this.getPackageName());
            imageIds[i] = imageResourceId;
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GyroscopeActivity.class);
                intent.putExtra("angleX", editTextX.getText().toString());
                intent.putExtra("angleY", editTextY.getText().toString());
                intent.putExtra("resourceId", imageIds[0]);
                intent.putExtra("finger", "null");
                intent.putExtra("number", tv.getText().toString());

                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GyroscopeActivity.class);
                intent.putExtra("angleX", editTextX.getText().toString());
                intent.putExtra("angleY", editTextY.getText().toString());
                intent.putExtra("resourceId", imageIds[1]);
                intent.putExtra("finger", "null");
                intent.putExtra("number", tv.getText().toString());

                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GyroscopeActivity.class);
                intent.putExtra("angleX", editTextX.getText().toString());
                intent.putExtra("angleY", editTextY.getText().toString());
                intent.putExtra("resourceId", imageIds[2]);
                intent.putExtra("finger", "null");
                intent.putExtra("number", tv.getText().toString());

                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlingActivity.class);
                intent.putExtra("resourceId", imageIds[3]);
                intent.putExtra("finger", "index");
                intent.putExtra("number", tv.getText().toString());

                startActivity(intent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlingActivity.class);
                intent.putExtra("resourceId", imageIds[4]);
                intent.putExtra("finger", "index");
                intent.putExtra("number", tv.getText().toString());

                startActivity(intent);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlingActivity.class);
                intent.putExtra("resourceId", imageIds[5]);
                intent.putExtra("finger", "index");
                intent.putExtra("number", tv.getText().toString());

                startActivity(intent);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlingActivity.class);
                intent.putExtra("resourceId", imageIds[6]);
                intent.putExtra("finger", "thumb");
                intent.putExtra("number", tv.getText().toString());

                startActivity(intent);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlingActivity.class);
                intent.putExtra("resourceId", imageIds[7]);
                intent.putExtra("finger", "thumb");
                intent.putExtra("number", tv.getText().toString());

                startActivity(intent);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlingActivity.class);
                intent.putExtra("resourceId", imageIds[8]);
                intent.putExtra("finger", "thumb");
                intent.putExtra("number", tv.getText().toString());

                startActivity(intent);
            }
        });
    }
}
