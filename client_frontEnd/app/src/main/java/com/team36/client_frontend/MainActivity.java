package com.team36.client_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String welcome_message = "Hi %s, see your rating below!";

    final static String users_name = "David";
    final static double users_rating = 4.2;

    private BottomNavigationView navigation;
    private ActivityOptionsCompat options;

    private int[] friend_pps = {R.drawable.baseline_person_outline_black_36};
    private String[] friend_names = {"Cam", "Josh", "Dave", "Cybs", "George", "Javier"};
    private double[] friend_ratings = {4.2, 4.8, 4.0, 4.2, 4.8, 4.4};

    private String[] journey_dates = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
    private double[] journeyRatings = {2.8, 4.6, 4.4, 3.4, 4.8, 4.2};

    // The below transitions between activities depending on which button in the navigation bar is pressed
    // This method appears in all other activities with the bottom navigation bar and acts in the same way
    private BottomNavigationView.OnNavigationItemSelectedListener myBottomNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /* The 'options' variable below creates the screen transition with the shared element
            being the bottom navigation bar */
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // Transitions to the home/main activity, if applicable
                    return true;
                case R.id.navigation_dashboard:
                    // Transitions to the dashboard activity, if applicable
                    Intent dashboard = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(dashboard, options.toBundle());
                    return true;
                case R.id.navigation_allJourneys:
                    // Transitions to the journeys activity, if applicable
                    Intent allJourneys = new Intent(MainActivity.this, JourneysActivity.class);
                    startActivity(allJourneys, options.toBundle());
                    return true;
                case R.id.navigation_allFriends:
                    // Transitions to the friends activity, if applicable
                    Intent allFriends = new Intent(MainActivity.this, FriendsActivity.class);
                    startActivity(allFriends, options.toBundle());
                    return true;
            }
            return false;
        }
    };

    // This method is for the navigation in the 'At a Glance' section and has the job of switching
    // what is displayed in the listView below it, being either basic friend or basic journey details
    private BottomNavigationView.OnNavigationItemSelectedListener myAtAGlanceNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_friends:
                    // This method displays basic information about the users' friends
                    atAGlance_friends();
                    return true;
                case R.id.navigation_journeys:
                    // This method displays basic information about the users' journeys
                    atAGlance_journeys();
                    return true;
            }
            return false;
            // TODO: Add transitions to the above?
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(myBottomNavigationListener);
        options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (MainActivity.this, navigation, "bottomNavView");
        // The navigation listener above 'listens' or detects when the user wants to change activity
        // and calls the 'myBottomNavigationListener' method when the user chooses

        BottomNavigationView navigation_atAGlance = findViewById(R.id.navigation_atAGlance);
        navigation_atAGlance.setOnNavigationItemSelectedListener(myAtAGlanceNavigationListener);
        // This navigation listener is for changing what is displayed in the 'At a Glance' section

        myMain(); /* This method acts as a (main) method to differentiate between my code and
                   * the code that was automatically generated by Android Studio */
    }

    // This method changes the 'At a Glance' section to display basic information about the user's friends
    private void atAGlance_friends(){
        ArrayList<listview_item> allRows = new ArrayList<>(); // To store all the rows to be displayed
        ListView myListView = findViewById(R.id.listView_atAGlance);

        // This for-loop assigns values to the components of each row
        if ((friend_names.length != 0) && (friend_names != null)) {
            for (int i = 0; i < friend_names.length; i++) {
                listview_item oneRow = new listview_item(); // Creates a new row
                // The below assigns each of the values to their corresponding components
                oneRow.setImage_pp(friend_pps[0]);
                oneRow.setText_name(friend_names[i]);
                oneRow.setRating_stars((float) friend_ratings[i]);
                oneRow.setRating_double(friend_ratings[i]);

                allRows.add(oneRow); // Adds each row to the list of rows to be added to the listView below
            }
        }

        // The friends_adapter is for adding all the users' friends to the listView
        myAdapter friends_adapter = new myAdapter(getApplicationContext(), allRows);
        myListView.setAdapter(friends_adapter);
    }

    // This method changes the 'At a Glance' section to display basic information about the user's journeys
    private void atAGlance_journeys(){
        ArrayList<listview_itemJourneys> allRows = new ArrayList<>(); // As stated previously
        ListView myListView = findViewById(R.id.listView_atAGlance);

        if ((journey_dates.length != 0) && (journey_dates != null)) {
            for (int i = 0; i < journey_dates.length; i++) {
                listview_itemJourneys oneRow = new listview_itemJourneys(); // Creates a new row
                // Below assigns values to their corresponding components
                oneRow.setText_journeyDate(journey_dates[i]);
                oneRow.setRating_stars((float) journeyRatings[i]);

                allRows.add(oneRow); // Adds each row to the list of rows
            }
        }

        // The journeys_adapter is is for adding all the user's journeys to the listView
        myAdapter_journeys journeys_adapter = new myAdapter_journeys(getApplicationContext(), allRows);
        myListView.setAdapter(journeys_adapter);
    }

    private void myMain(){
        atAGlance_friends(); // Causes friends to be displayed automatically without user input
        setWelcomeTextRating();

        // TODO: Fix the welcome message!
    }

    private void setWelcomeTextRating(){
        TextView textView_welcome = findViewById(R.id.textView_welcome);
        textView_welcome.setText(String.format(welcome_message, users_name));

        RatingBar ratingBar_user = findViewById(R.id.ratingBar_user);
        ratingBar_user.setRating((float)users_rating);
    }

    public void go_pressed(View view){
        Intent driving = new Intent(MainActivity.this, DrivingActivity.class);
        startActivity(driving);
    }
}
