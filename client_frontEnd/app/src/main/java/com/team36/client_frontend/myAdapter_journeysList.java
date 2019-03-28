package com.team36.client_frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class myAdapter_journeysList extends BaseAdapter {
    private ArrayList<listView_itemJourneys> myRows;
    private LayoutInflater myInflater;

    public myAdapter_journeysList(Context context, ArrayList<listView_itemJourneys> myRows) {
        this.myRows = myRows;
        myInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return myRows.size();
    }

    @Override
    public Object getItem(int position) {
        return myRows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        JourneysViews journeysViews;
        if (convertView == null){
            convertView = myInflater.inflate(R.layout.listview_rowjourneys, parent, false);
            // Gets all the components of each row/journey so their values can be set
            journeysViews = new JourneysViews();
            journeysViews.imageView_ratingImage = convertView.findViewById(R.id.imageView_ratingOverall);
            journeysViews.textView_journeyDate = convertView.findViewById(R.id.textView_dayDate);
            journeysViews.ratingBar_journeyOverall = convertView.findViewById(R.id.ratingBar_overallStars);
            journeysViews.textView_ratingDouble = convertView.findViewById(R.id.textView_overallDouble);
            journeysViews.imageView_ratingAcceleration = convertView.findViewById(R.id.imageView_acceleration);
            journeysViews.imageView_ratingBraking = convertView.findViewById(R.id.imageView_braking);
            journeysViews.imageView_ratingSpeed = convertView.findViewById(R.id.imageView_speed);
            journeysViews.imageView_ratingTime = convertView.findViewById(R.id.imageView_time);
            convertView.setTag(journeysViews);
        }else{
            journeysViews = (JourneysViews) convertView.getTag();
        }

        listView_itemJourneys currRow = (listView_itemJourneys) getItem(position);
        journeysViews.imageView_ratingImage.setImageResource(currRow.getImage_ratingImage());
        journeysViews.textView_journeyDate.setText(currRow.getText_nameDay());
        journeysViews.ratingBar_journeyOverall.setRating(currRow.getRating_ratingStars());
        journeysViews.textView_ratingDouble.setText(currRow.getRating_ratingString());
        journeysViews.imageView_ratingAcceleration.setImageResource(currRow.getImage_ratingAcceleration());
        journeysViews.imageView_ratingBraking.setImageResource(currRow.getImage_ratingBraking());
        journeysViews.imageView_ratingSpeed.setImageResource(currRow.getImage_ratingSpeed());
        journeysViews.imageView_ratingTime.setImageResource(currRow.getImage_ratingTime());

        return convertView;
    }

    class JourneysViews{
        ImageView imageView_ratingImage;
        TextView textView_journeyDate;
        RatingBar ratingBar_journeyOverall;
        TextView textView_ratingDouble;
        ImageView imageView_ratingAcceleration;
        ImageView imageView_ratingBraking;
        ImageView imageView_ratingSpeed;
        ImageView imageView_ratingTime;
    }
}
