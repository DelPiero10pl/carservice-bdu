package com.cars.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cars.data.controller.RepairController;
import com.cars.data.model.Repair;
import com.cars.ui.R;
import com.cars.ui.flow.Router;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class RepairRecyclerViewAdapter extends RealmBasedRecyclerViewAdapter<Repair, RepairRecyclerViewAdapter.ViewHolder> {
    public RepairRecyclerViewAdapter(Context context, RealmResults<Repair> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, final int i) {
        View v = inflater.inflate(R.layout.repair_item, viewGroup, false);
        RepairRecyclerViewAdapter.ViewHolder vh = new RepairRecyclerViewAdapter.ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
        Repair repair = realmResults.get(i);
        viewHolder.name.setText(repair.getName());
        viewHolder.id = repair.getId();
        viewHolder.date.setText(repair.getDate());
        viewHolder.serviceMillage.setText(repair.getMilage().toString());
        viewHolder.serviceCost.setText(repair.getParts().sum("price").toString());
        viewHolder.workshopCost.setText(String.valueOf(repair.getWorkshopCost()));
    }

    public void openByID(Integer  id) {
        Router.showRepair(getContext(), id);
    }

    public class ViewHolder extends RealmViewHolder {
        private final TextView name;
        private final TextView date;
        private final TextView serviceMillage;
        private final TextView serviceCost;
        private final TextView workshopCost;
        //private String name;
        private Integer id;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.servicing_name);
            date = (TextView) itemView.findViewById(R.id.service_date);
            serviceMillage = (TextView) itemView.findViewById(R.id.service_millage);
            serviceCost = (TextView) itemView.findViewById(R.id.service_cost);
            workshopCost = (TextView) itemView.findViewById(R.id.workshop_cost);
            //name.setText(this.name);
            itemView.findViewById(R.id.servicecard).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OPEN REPAIR BY ID", String.valueOf(id));
                    openByID(id);
                }
            });

            itemView.findViewById(R.id.servicecard).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClick(id);
                    return true;
                }
            });
        }
    }

    public void longClick(final Integer id) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setMessage("Usuń").setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new RepairController().remove(id);
                dialogInterface.dismiss();
            }
        }).create();
        alertDialog.show();
    }
}
