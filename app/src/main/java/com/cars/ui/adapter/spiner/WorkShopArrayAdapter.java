package com.cars.ui.adapter.spiner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.cars.data.model.Workshop;
import com.cars.ui.R;

import java.util.List;

public class WorkShopArrayAdapter extends ArrayAdapter<Workshop> {
    public WorkShopArrayAdapter(Context context, int resource, int textViewResourceId, List<Workshop> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Workshop workshop = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.work_shop_spiner_item, parent, false);
            viewHolder.workshop = (TextView) convertView.findViewById(R.id.workshop);
            viewHolder.workshopAdres = (TextView) convertView.findViewById(R.id.workshop_adres);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.workshop.setText(workshop.getName());
        viewHolder.workshopAdres.setText(workshop.getLocalization());
        // Return the completed view to render on screen
        return convertView;
    }

    public class ViewHolder {
        TextView workshopAdres,workshop;
    }
}
