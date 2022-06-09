package com.example.earthquakemonitor.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.earthquakemonitor.R;
import com.example.earthquakemonitor.data_models.EarthquakeDataCollection;
import com.example.earthquakemonitor.data_models.Feature;

public class EarthquakesListAdapter extends ArrayAdapter<Feature> {
    public EarthquakesListAdapter(@NonNull Context context, EarthquakeDataCollection earthQuakeCollection) {
        super(context, R.layout.list_item, earthQuakeCollection.getFeatures());
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Feature feature = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView textViewMagVal = convertView.findViewById(R.id.mag);
        TextView textViewLocation = convertView.findViewById(R.id.location);
        TextView textViewEventType = convertView.findViewById(R.id.eventType);
        TextView textViewTime = convertView.findViewById(R.id.time);

        textViewMagVal.setText(Float.toString(feature.getProperties().getMag()));
        textViewLocation.setText(feature.getProperties().getPlace());
        textViewEventType.setText(feature.getProperties().getType());
        textViewTime.setText(feature.getProperties().getTime().toString());

        return convertView;
    }
}
