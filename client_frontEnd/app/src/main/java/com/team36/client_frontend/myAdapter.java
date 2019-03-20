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

public class myAdapter extends BaseAdapter {
    private ArrayList<listview_item> myRows; // Stores all the rows/friends of the listView
    private LayoutInflater myInflater; // Inflater for adding each row to the listView

    public myAdapter(Context context, ArrayList<listview_item> myRows){
        this.myRows = myRows;
        myInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return myRows.size(); // Returns the number of rows in the listview
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
        // Lines 41 to 44 get a reference to the listview_row XML, effectively creating a new row
        if (convertView == null){
            convertView = myInflater.inflate(R.layout.listview_row, parent, false);
            setRow(position, convertView); // Calls setRow to set up the row with it's specific values
        }

        return convertView; // Returns the row to be displayed/added to the listview
    }

    private void setRow(int position, View convertView){
        // gets all the components of each row/friend so their values can be set
        ImageView imageView_pp = convertView.findViewById(R.id.imageView_friendPP);
        TextView textView_name = convertView.findViewById(R.id.textView_friendName);
        RatingBar ratingBar_friend = convertView.findViewById(R.id.ratingBar_friendRating);
        listview_item currRow = (listview_item) getItem(position);

        // The below code sets each of the components values of each row/friend
        imageView_pp.setImageResource(currRow.getImage_pp());
        textView_name.setText(currRow.getText_name());
        float rating = (float) (currRow.getRating_stars()); // Converts the rating to a float....
        ratingBar_friend.setRating(rating); // So that it can be passed to set their rating
    }
}
