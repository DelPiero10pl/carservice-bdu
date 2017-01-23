package com.cars.ui.adapter.spiner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cars.data.model.Workshop;
import com.cars.ui.R;
import io.realm.*;

public class WorkShopRecycleViewAdapter extends RealmBasedRecyclerViewAdapter<Workshop, WorkShopRecycleViewAdapter.ViewHolder> {

    private final Realm db;

    public WorkShopRecycleViewAdapter(Context context, RealmResults<Workshop> realmResults, boolean automaticUpdate, boolean animateResults, Realm db) {
        super(context, realmResults, automaticUpdate, animateResults);
        this.db = db;
    }

    @Override
    public WorkShopRecycleViewAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.work_shop_spiner_item, viewGroup, false);
        WorkShopRecycleViewAdapter.ViewHolder vh = new WorkShopRecycleViewAdapter.ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(WorkShopRecycleViewAdapter.ViewHolder viewHolder, int i) {
        Workshop workshop = realmResults.get(i);
        viewHolder.address.setText(workshop.getName());
        viewHolder.name.setText(workshop.getLocalization());
    }

    public class ViewHolder extends RealmViewHolder {
        private TextView address;
        private TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.workshop);
            address = (TextView) itemView.findViewById(R.id.workshop_adres);
        }

    }
}
