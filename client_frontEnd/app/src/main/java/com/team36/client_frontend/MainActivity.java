package com.team36.client_frontend;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigation;

    private int[] friend_pps = {R.drawable.baseline_person_outline_black_36};
    private String[] friend_names = {"Cam", "Josh", "Dave", "Cybs", "George", "Javier"};
    private double[] friend_ratings = {4.2, 4.8, 4.0, 4.2, 4.8, 4.4};

    private String[] journey_dates = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
    private double[] journeyRatings = {2.8, 4.6, 4.4, 3.4, 4.8, 4.2};

    private BottomNavigationView.OnNavigationItemSelectedListener myBottomNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, navigation, "bottomNavView");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    Intent dashboard = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(dashboard, options.toBundle());
                    return true;
                case R.id.navigation_allJourneys:
                    Intent allJourneys = new Intent(MainActivity.this, JourneysActivity.class);
                    startActivity(allJourneys, options.toBundle());
                    return true;
                case R.id.navigation_allFriends:
                    Intent allFriends = new Intent(MainActivity.this, FriendsActivity.class);
                    startActivity(allFriends, options.toBundle());
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

        navigation = findViewById(R.id.navigation);
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
