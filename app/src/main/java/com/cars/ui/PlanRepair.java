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
import com.cars.data.model.Repair;
import com.cars.ui.adapter.RepairRecyclerViewAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

public class PlanRepair extends AppCompatActivity {

    Context context;
    private RecyclerView repairList;
    private RepairRecyclerViewAdapter adapter;
    private Realm realm;
    private FloatingActionButton addRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_repair);
        context = this;
        realm = Realm.getDefaultInstance();
        initUI();

    }
    private void initUI() {
        addRepair = (FloatingActionButton) findViewById(R.id.addplanrepairFAButton);
        addRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,AddRepair.class));
            }
        });
        repairList = (RecyclerView) findViewById(R.id.planed_list);
        RealmResults<Repair> result = realm.where(Repair.class).equalTo("planed",true).findAll();
        adapter = new RepairRecyclerViewAdapter(this, result, true, true){
            @Override
            public void openByID(Integer id) {
                super.openByID(id);
            }
        };
        LinearLayoutManager recycleViewlayout = new LinearLayoutManager(this);
        recycleViewlayout.setOrientation(LinearLayoutManager.VERTICAL);
        repairList.setLayoutManager(recycleViewlayout);
        repairList.setAdapter(adapter);
    }
}
