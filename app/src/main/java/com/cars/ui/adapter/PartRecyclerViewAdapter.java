package com.cars.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cars.data.model.Part;
import com.cars.ui.R;
import com.cars.ui.dialog.DialogPartEdit;
import io.realm.*;

import java.util.List;

public class PartRecyclerViewAdapter extends RealmBasedRecyclerViewAdapter<Part, PartRecyclerViewAdapter.ViewHolder> {
    private Realm db;
    public PartRecyclerViewAdapter(Context context, RealmResults<Part> realmResults,boolean automaticUpdate, boolean animateResults, Realm db) {
        super(context, realmResults, automaticUpdate, animateResults);
        this.db = db;
    }

    public PartRecyclerViewAdapter(Context context, RealmResults<Part> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        int l = R.layout.form_part_list;
        if(db==null) l = R.layout.part_item;
        View v = inflater.inflate(l, viewGroup, false);
        PartRecyclerViewAdapter.ViewHolder vh = new PartRecyclerViewAdapter.ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
        final Part part = realmResults.get(i);
        viewHolder.name.setText(part.getName());
        viewHolder.price.setText(part.getPrice().toString());
        if(db!=null) {
            viewHolder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.beginTransaction();
                    part.deleteFromRealm();
                    db.commitTransaction();
                }
            });
            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DialogPartEdit b = new DialogPartEdit((Activity) getContext());
                    final EditText name = (EditText) b.getDialogView().findViewById(R.id.part_name);
                    final EditText cost = (EditText) b.getDialogView().findViewById(R.id.cost_part);
                    name.setText(part.getName());
                    cost.setText(part.getPrice().toString());
                    b.build(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.beginTransaction();
                            part.setName(name.getText().toString());
                            part.setPrice(Double.parseDouble(cost.getText().toString()));
                            db.commitTransaction();
                            notifyDataSetChanged();
                            dialogInterface.dismiss();
                        }
                    }).create().show();

                }
            });
        }
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
