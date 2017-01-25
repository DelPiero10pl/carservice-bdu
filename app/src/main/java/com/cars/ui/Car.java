package com.cars.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.cars.data.controller.CarController;
import com.cars.data.model.FuelTypUtil;
import com.cars.data.model.Repair;
import com.cars.ui.adapter.RepairRecyclerViewAdapter;
import com.cars.ui.flow.Router;
import io.realm.Realm;
import io.realm.RealmResults;

public class Car extends AppCompatActivity {

    private Context context;
    private FloatingActionButton addhistrepair;
    private TextView mileage,model,mark,capacity;
    private RecyclerView repairList;
    private Realm realm;
    private RepairRecyclerViewAdapter adapter;
    private int id;
    private FloatingActionButton edit;
    private TextView fuel;
    private TextView year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        context = this;
        Intent intent = getIntent();
        id = intent.getIntExtra(CarController.CAR_ID, 0);
        com.cars.data.model.Car car = new CarController().findByID(id);
        realm = Realm.getDefaultInstance();
        initUI();

        model.setText(car.getModel());
        mark.setText(car.getMark());
        mileage.setText(car.getMileage().toString());
        capacity.setText(car.getCapacity().toString());
        fuel.setText(FuelTypUtil.getFuelName(car.getFuelTyp()));
        year.setText(car.getProductionYear().toString());

        addhistrepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addHistory(context, id);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router.showEitCar(context, id);
            }
        });
    }

    private void initUI() {
        addhistrepair = (FloatingActionButton) findViewById(R.id.addhistoryrepairFAButton);
        mileage = (TextView) findViewById(R.id.mileage);
        model = (TextView)findViewById(R.id.model);
        mark = (TextView) findViewById(R.id.mark);
        capacity = (TextView) findViewById(R.id.capacity);
        fuel = (TextView) findViewById(R.id.fuel);
        year = (TextView) findViewById(R.id.year);
        repairList = (RecyclerView) findViewById(R.id.repair_list);
        RealmResults<Repair> result = realm.where(Repair.class).equalTo("car.id", id).notEqualTo("planed",true).findAll();
        adapter = new RepairRecyclerViewAdapter(this, result, true, true);
        LinearLayoutManager recycleViewlayout = new LinearLayoutManager(this);
        recycleViewlayout.setOrientation(LinearLayoutManager.VERTICAL);
        repairList.setLayoutManager(recycleViewlayout);
        repairList.setAdapter(adapter);
        edit = (FloatingActionButton) findViewById(R.id.edit_car);
    }

    @Override
    public void onBackPressed() {
        Router.showCarsList(this);
    }
}
