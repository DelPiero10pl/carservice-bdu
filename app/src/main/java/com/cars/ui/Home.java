package com.cars.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.cars.data.model.*;
import com.cars.data.model.Car;
import io.realm.Realm;

public class Home extends AppCompatActivity {

    private Context context;
    private TextView totalCost;
    private TextView carCount;
    private Realm realm;
    private Button car;
    private Button plan;
    private Button cost;
    private Button settings;
    private Number totalWorkshopcost;
    private Number totalPartCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        realm = Realm.getDefaultInstance();
        initUI();
        int totalCar = realm.where(Car.class).findAll().size();
        carCount.setText(String.valueOf(totalCar));

        totalWorkshopcost = realm.where(Repair.class).sum("workshopCost");
        totalPartCost = realm.where(Part.class).sum("price");

        totalCost.setText(String.valueOf(totalPartCost.doubleValue()+totalWorkshopcost.doubleValue()));


    }

    private void initUI() {
        totalCost = (TextView) findViewById(R.id.total_cost);
        carCount = (TextView) findViewById(R.id.car_count);
        car = (Button) findViewById(R.id.carbutton);
        plan = (Button) findViewById(R.id.planrepairbutton);
        cost = (Button) findViewById(R.id.costbutton);
        settings = (Button) findViewById(R.id.settingsbutton);

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CarList.class));
            }
        });

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,PlanRepair.class));
            }
        });

        cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,CostStat.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,SettingsApp.class));
            }
        });
    }
}
