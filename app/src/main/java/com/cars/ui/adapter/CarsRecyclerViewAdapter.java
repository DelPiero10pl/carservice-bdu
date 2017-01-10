package com.cars.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cars.data.model.Car;
import com.cars.data.model.FuelTypUtil;
import com.cars.ui.R;
import com.cars.ui.flow.CarFlow;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class CarsRecyclerViewAdapter extends RealmBasedRecyclerViewAdapter<Car,CarsRecyclerViewAdapter.ViewHolder>  {

    public CarsRecyclerViewAdapter(Context context, RealmResults<Car> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.car_item, viewGroup, false);
        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
        Car result = realmResults.get(i);
        viewHolder.id = result.getId();
        viewHolder.mark.setText(result.getMark());
        viewHolder.model.setText(result.getModel());
        viewHolder.mileage.setText(result.getMileage().toString());
        viewHolder.capacity.setText(result.getCapacity().toString());
        viewHolder.year.setText(result.getProductionYear().toString());
        viewHolder.fuel.setText(FuelTypUtil.getFuelName(result.getFuelTyp()));
    }

    public class ViewHolder extends RealmViewHolder {

        public TextView mark,model,year,mileage,capacity,fuel;
        public Integer id;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CarFlow.showCar(getContext(), id);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.d("LONG", "CLICK");
                    return true;
                }
            });

            this.mark = (TextView) itemView.findViewById(R.id.mark);
            this.model = (TextView) itemView.findViewById(R.id.model);
            this.year = (TextView) itemView.findViewById(R.id.year);
            this.mileage = (TextView) itemView.findViewById(R.id.mileage);
            this.capacity = (TextView) itemView.findViewById(R.id.capacity);
            this.fuel = (TextView) itemView.findViewById(R.id.fuel);
        }
    }
}
