package com.team36.client_frontend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int[] friend_pps = {R.drawable.baseline_person_outline_black_36};
    private String[] friend_names = {"Cam", "Josh", "Dave", "Cybs", "George", "Javier"};
    private double[] friend_ratings = {4.2, 4.8, 4.0, 4.2, 4.8, 4.4};

    private String[] journey_dates = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
    private double[] journeyRatings = {2.8, 4.6, 4.4, 3.4, 4.8, 4.2};

    private BottomNavigationView.OnNavigationItemSelectedListener myBottomNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_allJourneys:
                    return true;
                case R.id.navigation_allFriends:
                    return true;
            }
            return false;
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener myAtAGlanceNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_friends:
                    atAGlance_friends();
                    return true;
                case R.id.navigation_journeys:
                    atAGlance_journeys();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(myBottomNavigationListener);

        BottomNavigationView navigation_atAGlance = findViewById(R.id.navigation_atAGlance);
        navigation_atAGlance.setOnNavigationItemSelectedListener(myAtAGlanceNavigationListener);

        myMain();
    }

    private void atAGlance_friends(){
        ArrayList<listview_item> allRows = new ArrayList<>();
        ListView myListView = findViewById(R.id.listView_atAGlance);

        for (int i=0; i<friend_names.length; i++){
            listview_item oneRow = new listview_item();
            oneRow.setImage_pp(friend_pps[0]);
            oneRow.setText_name(friend_names[i]);
            oneRow.setRating_stars((float)friend_ratings[i]);

            allRows.add(oneRow);
        }

        myAdapter friends_adapter = new myAdapter(getApplicationContext(), allRows);
        myListView.setAdapter(friends_adapter);
    }

    private void atAGlance_journeys(){
        ArrayList<listview_itemJourneys> allRows = new ArrayList<>();
        ListView myListView = findViewById(R.id.listView_atAGlance);

        for (int i=0; i<journey_dates.length; i++){
            listview_itemJourneys oneRow = new listview_itemJourneys();
            oneRow.setText_journeyDate(journey_dates[i]);
            oneRow.setRating_stars((float)journeyRatings[i]);

            allRows.add(oneRow);
        }

        myAdapter_journeys journeys_adapter = new myAdapter_journeys(getApplicationContext(), allRows);
        myListView.setAdapter(journeys_adapter);
    }

    private void myMain(){
        atAGlance_friends();
    }
}
