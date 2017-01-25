package com.cars.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import com.cars.data.model.*;
import com.cars.data.model.Car;
import com.cars.ui.adapter.CarsRecyclerViewAdapter;
import com.cars.ui.flow.Router;
import io.realm.Realm;
import io.realm.RealmResults;

public class CarList extends AppCompatActivity {

    private Context context;
    private FloatingActionButton addcar;
    private RealmRecyclerView carList;
    private CarsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        context = this;
        initUI();
        addcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddCar.class));
            }
        });

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Car> nyTimesStories =
                realm.where(Car.class).findAll();
        adapter = new CarsRecyclerViewAdapter(this, nyTimesStories, true, true);
        carList.setAdapter(adapter);

    }

    private void initUI() {
        addcar = (FloatingActionButton) findViewById(R.id.addcarFAButton);
        carList = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
    }

    @Override
    public void onBackPressed() {
        Router.open(this, Home.class);
    }
}
