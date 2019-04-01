package com.team36.client_frontend;
// David Parrish - 201232252

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class My_Adapter_JourneysMain extends BaseAdapter {
    private ArrayList<ListView_ItemNormal> myRows; // Stores all the rows/journeys of the listView
    private LayoutInflater myInflater; // Inflater for adding each row to the listView

    public My_Adapter_JourneysMain(Context context, ArrayList<ListView_ItemNormal> myRows){
        this.myRows = myRows;
        myInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return myRows.size(); // Returns the number of rows in the listView
    }

    @Override
    public Object getItem(int position) {
        return myRows.get(position); // Returns which row is needing to be set
    }

    @Override
    public long getItemId(int position) {
        return position; // Returns the ID of the specified row
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Lines 41 to 54 get's a reference to the listview_rowjourneys.xml, effectively creating a new row
        JourneyViews journeyViews;
        if (convertView == null){
            convertView = myInflater.inflate(R.layout.listview_row, parent, false);
            // Gets all the components of each row/journey so their values can be set
            journeyViews = new JourneyViews();
            journeyViews.imageView_ratingImage = convertView.findViewById(R.id.imageView_ratingImage);
            journeyViews.textView_journeyDate = convertView.findViewById(R.id.textView_nameDay);
            journeyViews.ratingBar_journey = convertView.findViewById(R.id.ratingBar_ratingStars);
            journeyViews.textView_ratingDouble = convertView.findViewById(R.id.textView_ratingDouble);
            convertView.setTag(journeyViews);
        }else{
            journeyViews = (JourneyViews) convertView.getTag();
        }

        // Below sets each of the components' values of each row/journey
        ListView_ItemNormal currRow = (ListView_ItemNormal) getItem(position);
        journeyViews.imageView_ratingImage.setImageResource(currRow.getImage_ratingImage());
        journeyViews.textView_journeyDate.setText(currRow.getText_nameDay());
        journeyViews.ratingBar_journey.setRating(currRow.getRating_ratingStars());
        journeyViews.textView_ratingDouble.setText(currRow.getRating_ratingString());

        return convertView; // Returns the row to be displayed/added to the listView
    }

    class JourneyViews{
        ImageView imageView_ratingImage;
        TextView textView_journeyDate;
        RatingBar ratingBar_journey;
        TextView textView_ratingDouble;
    }
}
