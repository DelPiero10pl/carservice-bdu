package com.cars.ui.adapter.spiner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cars.data.model.Car;
import com.cars.ui.R;

import java.util.List;

public class CarArrayAdapter extends BaseAdapter {
    private List<Car> list;
    private Context context;
    public CarArrayAdapter(Context context, List<Car> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Car getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Car car = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.car_spiner_item, parent, false);

            viewHolder.mark = (TextView) convertView.findViewById(R.id.mark);
            viewHolder.model = (TextView) convertView.findViewById(R.id.model);


            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.mark.setText(car.getMark());
        viewHolder.model.setText(car.getModel());
        // Return the completed view to render on screen
        return convertView;
    }

    public class ViewHolder {
        public TextView mark,model;
    }

}
