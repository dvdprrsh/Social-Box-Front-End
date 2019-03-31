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

public class My_Adapter_FriendMain extends BaseAdapter {
    private ArrayList<ListView_ItemNormal> myRows; // Stores all the rows/friends of the listView
    private LayoutInflater myInflater; // Inflater for adding each row to the listView

    public My_Adapter_FriendMain(Context context, ArrayList<ListView_ItemNormal> myRows){
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
        // Lines 41 to 44 get a reference to the listview_row.xml, effectively creating a new row
        FriendViews friendViews;
        if (convertView == null){
            convertView = myInflater.inflate(R.layout.listview_row, parent, false);
            // Gets all the components of each row/friend so their values can be set
            friendViews = new FriendViews();
            friendViews.imageView_pp = convertView.findViewById(R.id.imageView_ratingImage);
            friendViews.textView_name = convertView.findViewById(R.id.textView_nameDay);
            friendViews.ratingBar_friend = convertView.findViewById(R.id.ratingBar_ratingStars);
            friendViews.textView_ratingDouble = convertView.findViewById(R.id.textView_ratingDouble);
            convertView.setTag(friendViews);
        }else {
            friendViews = (FriendViews) convertView.getTag();
        }

        ListView_ItemNormal currRow = (ListView_ItemNormal) getItem(position);

        // The below code sets each of the components values of each row/friend
        friendViews.imageView_pp.setImageResource(currRow.getImage_ratingImage());
        friendViews.textView_name.setText(currRow.getText_nameDay());
        friendViews.ratingBar_friend.setRating(currRow.getRating_ratingStars());
        friendViews.textView_ratingDouble.setText(currRow.getRating_ratingString());

        return convertView; // Returns the row to be displayed/added to the listView
    }

    class FriendViews{
        ImageView imageView_pp;
        TextView textView_name;
        RatingBar ratingBar_friend;
        TextView textView_ratingDouble;
    }
}
