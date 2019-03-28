package com.team36.client_frontend;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    public final String TITLE = "Your Home";
    private final int[] IMAGE_VIEWS = {R.id.imageView_acceleration, R.id.imageView_braking, R.id.imageView_speed, R.id.imageView_time};

    private static final int REQUEST_LOCATION = 1111;
    private View returnView;

    final private String welcome_message = "Hi %s, see your rating below!";

    private String users_name;
    private double users_rating;

    private int[] friend_pps = {R.drawable.ic_person_outline_black_48dp};
    private String[] friend_names = {"Cam", "Josh", "Dave", "Cybs", "George", "Javier"};
    private double[] friend_ratings = {4.0, 5.0, 4.0, 4.0, 5.0, 4.5};

    private String[] journey_dates = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
    private double[] journeyRatings = {3.0, 4.5, 4.5, 3.5, 5.0, 4.0};

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
        }
    };

    private ListView.OnItemClickListener myItemClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_main, container, false);

        LoggedIn_User loggedIn_user = new LoggedIn_User();
        users_name = loggedIn_user.user_firstName;
        users_rating = loggedIn_user.user_overall;

        BottomNavigationView navigation_atAGlance = returnView.findViewById(R.id.navigation_atAGlance);
        navigation_atAGlance.setOnNavigationItemSelectedListener(myAtAGlanceNavigationListener);
        // This navigation listener is for changing what is displayed in the 'At a Glance' section

        myMain(); /* This method acts as a (main) method to differentiate between my code and
                   * the code that was automatically generated by Android Studio */

        Button button_go = returnView.findViewById(R.id.button_go);
        button_go.setOnClickListener(this::onClick);

        return returnView;
    }


    private void myMain(){
        atAGlance_friends(); // Causes friends to be displayed automatically without user interaction
        setWelcomeTextRating();
    }

    // This method changes the 'At a Glance' section to display basic information about the user's friends
    private void atAGlance_friends(){
        ArrayList<listView_item> allRows = new ArrayList<>(); // To store all the rows to be displayed
        ListView myListView = returnView.findViewById(R.id.listView_atAGlance);
        myListView.setOnItemClickListener(myItemClickListener);

        // This for-loop assigns values to the components of each row
        if (friend_names != null) {
            for (int i = 0; i < friend_names.length; i++) {
                listView_item oneRow = new listView_item(); // Creates a new row
                // The below assigns each of the values to their corresponding components
                setImage(friend_ratings[i], oneRow);
                oneRow.setText_nameDay(friend_names[i]);
                oneRow.setRating_ratingStars((float) friend_ratings[i]);
                oneRow.setRating_ratingString(friend_ratings[i]);

                allRows.add(oneRow); // Adds each row to the list of rows to be added to the listView below
            }
        }

        // The friends_adapter is for adding all the users' friends to the listView
        myAdapter friends_adapter = new myAdapter(getContext(), allRows);
        myListView.setAdapter(friends_adapter);
    }

    // This method changes the 'At a Glance' section to display basic information about the user's journeys
    private void atAGlance_journeys(){
        ArrayList<listView_item> allRows = new ArrayList<>(); // As stated previously
        ListView myListView = returnView.findViewById(R.id.listView_atAGlance);
        myListView.setOnItemClickListener(myItemClickListener);

        if (journey_dates != null) {
            for (int i = 0; i < journey_dates.length; i++) {
                listView_item oneRow = new listView_item(); // Creates a new row
                // Below assigns values to their corresponding components
                setImage(journeyRatings[i], oneRow);
                oneRow.setText_nameDay(journey_dates[i]);
                oneRow.setRating_ratingStars(journeyRatings[i]);
                oneRow.setRating_ratingString(journeyRatings[i]);

                allRows.add(oneRow); // Adds each row to the list of rows
            }
        }

        // The journeys_adapter is is for adding all the user's journeys to the listView
        myAdapter_journeys journeys_adapter = new myAdapter_journeys(getContext(), allRows);
        myListView.setAdapter(journeys_adapter);
    }

    // Sets the image for each card view dependant on the rating of each section rating
    private void setImage(Double sectionRating, listView_item oneRow){
        switch (Double.toString(sectionRating)){
            case "1.0":
            case "1.5":
                oneRow.setImage_ratingImage(R.drawable.ic_sentiment_very_dissatisfied_black_48dp);
                break;

            case "2.0":
            case "2.5":
                oneRow.setImage_ratingImage(R.drawable.ic_sentiment_dissatisfied_black_48dp);
                break;

            case "3.0":
                oneRow.setImage_ratingImage(R.drawable.ic_sentiment_neutral_black_48dp);
                break;

            case "3.5":
                oneRow.setImage_ratingImage(R.drawable.ic_sentiment_neutral_black_48dp);
                break;

            case "4.0":
                oneRow.setImage_ratingImage(R.drawable.ic_sentiment_satisfied_black_48dp);
                break;

            case "4.5":
                oneRow.setImage_ratingImage(R.drawable.ic_sentiment_very_satisfied_black_48dp);
                break;

            case "5.0":
                oneRow.setImage_ratingImage(R.drawable.ic_whatshot_black_48dp);
        }
    }

    private void setWelcomeTextRating(){
        TextView textView_welcome = returnView.findViewById(R.id.textView_welcome);
        textView_welcome.setText(String.format(welcome_message, users_name));

        RatingBar ratingBar_user = returnView.findViewById(R.id.ratingBar_user);
        ratingBar_user.setRating((float)users_rating);
    }

    // Opens the driving activity after checking whether permission for accessing fine and coarse location
    public void onClick(View view){
        boolean requested = false;

        while (ContextCompat.checkSelfPermission(returnView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
           if (!requested){
               ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
               requested = true;
           }
        }

        Intent driving = new Intent(getActivity(), DrivingEsri.class);
        startActivity(driving);
    }
}
