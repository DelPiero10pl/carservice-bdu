package com.ui.car.carsui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class CarList extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        View r = (View) findViewById(R.id.carcard);

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Car.class));
            }
        });
    }
}
