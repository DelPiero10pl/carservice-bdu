package com.cars.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cars.data.controller.RepairController;
import com.cars.data.model.FuelTypUtil;
import com.cars.data.model.Part;
import com.cars.data.model.Photo;
import com.cars.data.model.Repair;
import com.cars.ui.adapter.PartRecyclerViewAdapter;
import com.cars.ui.adapter.PhotoRecycleViewAdapter;
import com.cars.ui.flow.Router;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tonicartos.superslim.LayoutManager;
import io.realm.Realm;
import io.realm.RealmResults;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Servicing extends AppCompatActivity implements OnMapReadyCallback {

    private Context context;
    private Intent intent;
    private int id;
    private TextView mark;
    private TextView model;
    private TextView capicity;
    private TextView mileage;
    private TextView fuel;
    private Realm realm;
    private Repair modelDB;
    private TextView serviceName;
    private TextView serviceDate;
    private TextView serviceMileage;
    private TextView serviceCost;
    private TextView workshopCost;
    private RecyclerView partsList;
    private CardView workShopCard;
    private TextView workshopName;
    private TextView workShopAresss;
    private RecyclerView photoList;
    private FloatingActionButton edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicing);
        context = this;
        intent = getIntent();
        id = intent.getIntExtra(RepairController.REPAIR_ID, 0);
        initUI();
        realm = Realm.getDefaultInstance();

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        modelDB = realm.where(Repair.class).equalTo("id", id).findFirst();
        mark.setText(modelDB.getCar().getMark());
        model.setText(modelDB.getCar().getModel());
        capicity.setText(modelDB.getCar().getCapacity().toString());
        mileage.setText(modelDB.getCar().getMileage().toString());
        fuel.setText(FuelTypUtil.getFuelName(modelDB.getCar().getFuelTyp()));

        serviceName.setText(modelDB.getName());
        serviceDate.setText(modelDB.getDate());
        serviceMileage.setText(modelDB.getMilage()+" km");
        double sumParts = modelDB.getParts().sum("price").doubleValue();
        serviceCost.setText(String.valueOf(sumParts));
        workshopCost.setText(String.valueOf(modelDB.getWorkshopCost()));

        PartRecyclerViewAdapter adapter = new PartRecyclerViewAdapter(this, modelDB.getParts().where().findAll(), true, true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        partsList.setLayoutManager(llm);
        partsList.setAdapter(adapter);

        if(modelDB.getWorkshop()!=null) {
            workShopCard.setVisibility(View.VISIBLE);
            workshopName.setText(modelDB.getWorkshop().getName());
            workShopAresss.setText(modelDB.getWorkshop().getLocalization());
        } else  {
            workShopCard.setVisibility(View.GONE);
        }
        PhotoRecycleViewAdapter photoAdapter = new PhotoRecycleViewAdapter(this, realm.where(Photo.class).equalTo("repair.id", id).findAll(), true, true);
        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        photoList.setLayoutManager(llm2);
        photoList.setAdapter(photoAdapter);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router.toEditRepair(context, id);
            }
        });

    }


    private void initUI() {
        mark = (TextView) findViewById(R.id.mark);
        model = (TextView) findViewById(R.id.model);
        capicity = (TextView) findViewById(R.id.capacity);
        mileage = (TextView) findViewById(R.id.mileage);
        fuel = (TextView) findViewById(R.id.fuel);

        serviceName = (TextView) findViewById(R.id.service_name);
        serviceDate = (TextView) findViewById(R.id.service_date);
        serviceMileage = (TextView) findViewById(R.id.service_mileage);
        serviceCost = (TextView) findViewById(R.id.service_cost);
        workshopCost = (TextView) findViewById(R.id.workshop_cost);

        partsList = (RecyclerView) findViewById(R.id.service_parts_list);

        workShopCard = (CardView) findViewById(R.id.workshop_card);
        workshopName = (TextView) findViewById(R.id.workshop_name);
        workShopAresss = (TextView) findViewById(R.id.workshop_adress);

        photoList = (RecyclerView) findViewById(R.id.photo_list);

        edit = (FloatingActionButton) findViewById(R.id.edit);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(modelDB.getWorkshop()!=null) {
            Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
            try {
                List<Address> geo = geocoder.getFromLocationName(modelDB.getWorkshop().getLocalization(), 1);


                LatLng sydney = new LatLng(geo.get(0).getLatitude(), geo.get(0).getLongitude());

                googleMap.addMarker(new MarkerOptions().position(sydney)
                        .title("Marker in Sydney"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
