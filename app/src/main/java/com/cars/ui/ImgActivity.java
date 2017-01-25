package com.cars.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        ImageView img = (ImageView) findViewById(R.id.img);
        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("img"));
        img.setImageBitmap(bitmap);
    }
}
