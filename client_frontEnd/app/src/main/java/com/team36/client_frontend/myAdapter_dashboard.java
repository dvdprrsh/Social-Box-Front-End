package com.team36.client_frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class myAdapter_dashboard extends BaseAdapter {
    private ArrayList<listview_itemDashboard> myRows; // Stores all the rows of the listView
    private LayoutInflater myInflater; // Inflater for adding each row to the listView

    public myAdapter_dashboard(Context context, ArrayList<listview_itemDashboard> myRows){
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
        // Lines 41 to 44 get's a reference to the listView_rowdashboard.xml, effectively creating a new row
        if (convertView == null){
            convertView = myInflater.inflate(R.layout.listview_rowdashboard, parent, false);
            setRow(position, convertView); // Calls setRow to set up the row with it's specific values
        }

        return convertView; // Returns the row to be displayed/added to the listView
    }

    private void setRow(int position, View convertView){
        // Gets all the components of each row/journey so their values can be set
        TextView textView_section = convertView.findViewById(R.id.textView_dashboardSection);
        RatingBar ratingBar_dashboard = convertView.findViewById(R.id.ratingBar_dashboard);
        TextView textView_rating = convertView.findViewById(R.id.textView_dashboardRating);
        listview_itemDashboard currRow = (listview_itemDashboard) getItem(position);

        // The below code sets each of the components values of each row/journey
        textView_section.setText(currRow.getText_section());
        float rating = (float) (currRow.getRating_stars()); // Converts the rating to a float so that....
        ratingBar_dashboard.setRating(rating); // ....it can be passed to set the rating for the specified journey
        textView_rating.setText(currRow.getText_rating());
    }
}
