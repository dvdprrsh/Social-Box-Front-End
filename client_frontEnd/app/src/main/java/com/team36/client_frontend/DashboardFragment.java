package com.team36.client_frontend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    public final String TITLE = "Your Dashboard";

    private View returnView;
    private final String WELCOME_DASHBOARD = "%s's Dashboard";
    private final String[] SECTIONS = {"Acceleration", "Braking", "Speed", "Time of Day"};
    private Double[] RATINGS = {4.0, 5.0, 5.0, 3.5};
    private final String USERS_NAME = MainFragment.users_name;
    private final double USERS_RATING = MainFragment.users_rating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        myMain();
        return returnView;
    }

    private void myMain(){
        TextView textView_welcome = returnView.findViewById(R.id.textView_welcomeDashboard);
        textView_welcome.setText(String.format(WELCOME_DASHBOARD, USERS_NAME));

        RatingBar ratingBar_dashboard = returnView.findViewById(R.id.ratingBar_dashboardOverall);
        ratingBar_dashboard.setRating((float)USERS_RATING);

        displayStatistics();
    }

    private void displayStatistics(){
        ArrayList<listview_itemDashboard> allRows = new ArrayList<>(); // As stated previously
        ListView myListView = returnView.findViewById(R.id.listView_dashboard);

            for (int i = 0; i < SECTIONS.length; i++) {
                listview_itemDashboard oneRow = new listview_itemDashboard(); // Creates a new row
                // Below assigns values to their corresponding components
                oneRow.setText_section(SECTIONS[i]);
                oneRow.setRating_stars(RATINGS[i]);
                oneRow.setText_rating(RATINGS[i]);

                allRows.add(oneRow); // Adds each row to the list of rows
            }

        // The dashboard_adapter is is for adding all the user's statistics to the listView
        myAdapter_dashboard dashboard_adapter = new myAdapter_dashboard(getContext(), allRows);
        myListView.setAdapter(dashboard_adapter);
    }
}
