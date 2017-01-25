package com.cars.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cars.data.controller.RepairController;
import com.cars.data.model.Repair;
import com.cars.ui.R;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class WorkShopCostRecycleView extends RealmBasedRecyclerViewAdapter<Repair, WorkShopCostRecycleView.ViewHolder> {
public WorkShopCostRecycleView (Context context, RealmResults<Repair> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
        }

@Override
public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, final int i) {
        View v = inflater.inflate(R.layout.part_item, viewGroup, false);
        WorkShopCostRecycleView.ViewHolder vh = new WorkShopCostRecycleView .ViewHolder((LinearLayout) v);
        return vh;
        }

@Override
public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
        Repair repair = realmResults.get(i);
        viewHolder.name.setText(repair.getWorkshop().getName());
        viewHolder.price.setText(String.valueOf(repair.getWorkshopCost()));
        }


    public class ViewHolder extends RealmViewHolder {
        public TextView name, price;
        public Button edit,remove;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.part);
            price = (TextView) itemView.findViewById(R.id.price);
            edit = (Button) itemView.findViewById(R.id.edit_part);
            remove = (Button) itemView.findViewById(R.id.remove_part);
        }
    }

}
