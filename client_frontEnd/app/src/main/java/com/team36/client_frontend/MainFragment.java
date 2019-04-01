package com.team36.client_frontend;
// David Parrish - 201232252

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    public final String TITLE = "Your Home";

    private static final int REQUEST_LOCATION = 1111;
    private View returnView;

    final private String welcome_message = "Hi %s, see your rating below!";

    private String users_name;
    private double users_rating;

    private String[] friend_names = {"Cam", "Josh", "Dave", "Cybs", "George", "Javier"};
    private double[] friend_ratings = {4.0, 5.0, 4.0, 4.0, 5.0, 4.5};

    private String[] journey_dates = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
    private double[] journey_ratings = {3.0, 4.5, 4.5, 3.5, 5.0, 4.0};

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

    private ListView.OnItemClickListener myClickListenerFriend = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    // TODO: Fix this method!
    private ListView.OnItemClickListener myClickListenerJourney = new ListView.OnItemClickListener(){
        // This method is called when one of the journeys has been clicked/tapped
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            JourneyFragment journeyFragment = new JourneyFragment();
            Bundle arguments = new Bundle(); // For passing data across to the 'journeyFragment' fragment

            // Finds all the views which data needs to be passed across to 'journeyFragment'
            TextView dateName = view.findViewById(R.id.textView_dayDate);
            RatingBar ratingOverall = view.findViewById(R.id.ratingBar_overallStars);
            ImageView ratingAcceleration = view.findViewById(R.id.imageView_acceleration);
            ImageView ratingBraking = view.findViewById(R.id.imageView_braking);
            ImageView ratingSpeed = view.findViewById(R.id.imageView_speed);
            ImageView ratingTime = view.findViewById(R.id.imageView_time);

            // Adds values to the 'arguments' bundle so that the data stored in it can be used
            arguments.putString("dateName", dateName.getText().toString());
            arguments.putFloat("ratingOverall", ratingOverall.getRating());
            arguments.putString("ratingAcceleration", ratingAcceleration.getTag().toString());
            arguments.putString("ratingBraking", ratingBraking.getTag().toString());
            arguments.putString("ratingSpeed", ratingSpeed.getTag().toString());
            arguments.putString("ratingTime", ratingTime.getTag().toString());

            journeyFragment.setArguments(arguments); // Sets the arguments for the fragment

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Assigns values to the fragment Transaction
            fragmentTransaction
                    .replace(R.id.fragment_layout, journeyFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (fragmentManager.getBackStackEntryCount() < 1){
                fragmentTransaction.addToBackStack(null); // To prevent multiple 'back' button presses
            }
            fragmentTransaction.commit();
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
        ArrayList<ListView_ItemNormal> allRows = new ArrayList<>(); // To store all the rows to be displayed
        ListView myListView = returnView.findViewById(R.id.listView_atAGlance);
        myListView.setOnItemClickListener(myClickListenerFriend);

        // This for-loop assigns values to the components of each row
        if (friend_names != null) {
            for (int i = 0; i < friend_names.length; i++) {
                ListView_ItemNormal oneRow = new ListView_ItemNormal(); // Creates a new row
                // The below assigns each of the values to their corresponding components
                oneRow.setImage_ratingImage(new ImageCalculator(friend_ratings[i]).image);
                oneRow.setText_nameDay(friend_names[i]);
                oneRow.setRating_ratingStars((float) friend_ratings[i]);
                oneRow.setRating_ratingString(friend_ratings[i]);

                allRows.add(oneRow); // Adds each row to the list of rows to be added to the listView below
            }
        }

        // The friends_adapter is for adding all the users' friends to the listView
        My_Adapter_FriendMain friends_adapter = new My_Adapter_FriendMain(getContext(), allRows);
        myListView.setAdapter(friends_adapter);
    }

    // This method changes the 'At a Glance' section to display basic information about the user's journeys
    private void atAGlance_journeys(){
        ArrayList<ListView_ItemNormal> allRows = new ArrayList<>(); // As stated previously
        ListView myListView = returnView.findViewById(R.id.listView_atAGlance);
        myListView.setOnItemClickListener(myClickListenerJourney);

        if (journey_dates != null) {
            for (int i = 0; i < journey_dates.length; i++) {
                ListView_ItemNormal oneRow = new ListView_ItemNormal(); // Creates a new row
                // Below assigns values to their corresponding components
                oneRow.setImage_ratingImage(new ImageCalculator(journey_ratings[i]).image);
                oneRow.setText_nameDay(journey_dates[i]);
                oneRow.setRating_ratingStars(journey_ratings[i]);
                oneRow.setRating_ratingString(journey_ratings[i]);

                allRows.add(oneRow); // Adds each row to the list of rows
            }
        }

        // The journeys_adapter is is for adding all the user's journeys to the listView
        My_Adapter_JourneysMain journeys_adapter = new My_Adapter_JourneysMain(getContext(), allRows);
        myListView.setAdapter(journeys_adapter);
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
