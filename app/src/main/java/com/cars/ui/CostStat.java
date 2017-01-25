package com.cars.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.cars.data.model.Part;
import com.cars.data.model.Repair;
import com.cars.ui.adapter.PartRecyclerViewAdapter;
import com.cars.ui.adapter.WorkShopCostRecycleView;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CostStat extends AppCompatActivity {

    private Context context;
    private Realm realm;
    private RecyclerView partsList;
    private RecyclerView workshopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_stat);
        realm = Realm.getDefaultInstance();
        context = this;
        initUI();
        RealmResults<Part> parts = realm.where(Part.class).findAll();
        PartRecyclerViewAdapter adapter1 = new PartRecyclerViewAdapter(context, parts, true, true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        partsList.setLayoutManager(llm);
        partsList.setAdapter(adapter1);

        LinearLayoutManager llm2 = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        workshopList.setLayoutManager(llm2);
        RealmResults<Repair> repairs = realm.where(Repair.class).notEqualTo("workshopCost", 0d).findAll();
        WorkShopCostRecycleView adapter2 = new WorkShopCostRecycleView(context, repairs, true, true);
        workshopList.setAdapter(adapter2);

    }

    private void initUI() {
        partsList = (RecyclerView) findViewById(R.id.parts_rc);
        workshopList = (RecyclerView) findViewById(R.id.workshop_rc);
    }

}
