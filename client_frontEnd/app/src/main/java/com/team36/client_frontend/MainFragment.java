package com.team36.client_frontend;
// David Parrish - 201232252

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {
    private static final int REQUEST_LOCATION = 1111;
    private final int GLANCE_FRIENDS = 0;
    private final int GLANCE_JOURNEYS = 1;
    private View returnView;
    final private String WELCOME_MESSAGE = "Hi %s, see your rating below!";
    private TabLayout tabLayout;
    private LoggedIn_User loggedIn_user;
    private String users_name;
    private double users_rating;
    private JSONObject json;
    private JSONArray jsonTrips;
    private JSONObject frienddata;
    private String[] users_friends = null;
    public List<TripInformation> TripList = new ArrayList<>();
    public List<FriendInformation> FriendList = new ArrayList<>();

    private double[][] friend_ratings = {{4.5, 4.0, 5.0, 5.0}, {3.0, 4.0, 4.0, 3.5}, {4.5, 4.0, 4.0, 3.5}, {5.0, 4.0, 4.0, 3.5}, {4.0, 4.0, 4.5, 3.5}, {3.5, 3.5, 4.0, 4.0}};
    private Map<String, double[]> friends = new HashMap<String, double[]>(){
        { 
            put("Cam", friend_ratings[0]);
            put("Josh", friend_ratings[1]);
            put("Dave", friend_ratings[2]);
            put("Cybs", friend_ratings[3]);
            put("George", friend_ratings[4]);
            put("Javier", friend_ratings[5]);
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
        }
    };

    private ListView.OnItemClickListener myClickListenerFriend = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FriendFragment friendFragment = new FriendFragment();
            // Finds all the views which data needs to be passed across to 'journeyFragment'
            TextView dateName = view.findViewById(R.id.textView_nameDay);
            String friendName = dateName.getText().toString();
            RatingBar ratingOverall = view.findViewById(R.id.ratingBar_ratingStars);

            FriendInformation selectedFriend = FriendList.get(position);
            double[] selectedScores = selectedFriend.getFriendScores();

            new OpenFriendJourneyFragment(friendFragment, null, getActivity().getSupportFragmentManager(), friendName, ratingOverall.getRating(), String.valueOf(selectedScores[0]), String.valueOf(selectedScores[1]), String.valueOf(selectedScores[2]), String.valueOf(selectedScores[3]), null);
        }
    };

    private ListView.OnItemClickListener myClickListenerJourney = new ListView.OnItemClickListener(){
        // This method is called when one of the journeys has been clicked/tapped
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            JourneyFragment journeyFragment = new JourneyFragment();
            double[] coords = {};
            try {
                coords = getCoords(((TripList.size()-1) - position));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Finds all the views which data needs to be passed across to 'journeyFragment'
            TextView dateName = view.findViewById(R.id.textView_nameDay);
            String journeyDate = dateName.getText().toString();
            RatingBar ratingOverall = view.findViewById(R.id.ratingBar_ratingStars);
            TripInformation selectedTrip = TripList.get(position);

            double[] selectedScores = selectedTrip.getScores();

            new OpenFriendJourneyFragment(null, journeyFragment, getActivity().getSupportFragmentManager(), journeyDate, ratingOverall.getRating(), String.valueOf(selectedScores[0]), String.valueOf(selectedScores[1]), String.valueOf(selectedScores[2]), String.valueOf(selectedScores[3]),coords);
        }
    };

    private double[] getCoords(int position) throws JSONException {

        JSONObject trip = (jsonTrips.getJSONObject(position));
        JSONArray lat = trip.getJSONArray("lat");
        JSONArray longs = trip.getJSONArray("long");
        double[] coords = new double[(lat.length() * 2)];
        int x = 0;
        for (int i = 0; i < coords.length; i++) {
            coords[i] = longs.getDouble(x);
            i++;
            coords[i] = lat.getDouble(x);
            x++;
        }

        return coords;
    }

    private TabLayout.OnTabSelectedListener myTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if  (tab.getPosition() == GLANCE_FRIENDS){
                atAGlance_friends();
            }else if (tab.getPosition() == GLANCE_JOURNEYS){
                atAGlance_journeys();
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            ListView myListView = returnView.findViewById(R.id.listView_atAGlance);
            myListView.setVisibility(View.VISIBLE);
            TextView textView_errorMain = returnView.findViewById(R.id.textView_errorMain);
            textView_errorMain.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_main, container, false);

        BaseActivity baseActivity = (BaseActivity) getActivity();
        loggedIn_user = baseActivity.loggedIn_user;
        FriendList = baseActivity.FriendList;
        TripList = baseActivity.TripList;

        try {
            frienddata = baseActivity.friendsjson;
            json = baseActivity.josn;
            jsonTrips = json.getJSONArray("trips");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        users_name = loggedIn_user.user_firstName;
        users_rating = loggedIn_user.user_overall;
        users_friends = loggedIn_user.user_friendIDs;

        tabLayout = returnView.findViewById(R.id.tabLayout_atGlance);
        tabLayout.addOnTabSelectedListener(myTabSelectedListener);
        // This tab listener is for changing what is displayed in the 'At a Glance' section

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

        // This for-loop assigns values to the components of each row
        if (FriendList.size() > 0) {
            myListView.setOnItemClickListener(myClickListenerFriend);
            OverallCalculator overallCalculator;
            for (int i = 0; i < FriendList.size(); i++) {
                overallCalculator = new OverallCalculator(FriendList.get(i).getFriendScores());
                double overall = overallCalculator.overallRating;

                ListView_ItemNormal oneRow = new ListView_ItemNormal(); // Creates a new row
                // The below assigns each of the values to their corresponding components
                oneRow.setImage_ratingImage(new ImageCalculator(overall).image);
                oneRow.setText_nameDay(FriendList.get(i).friendUsername);
                oneRow.setRating_ratingStars((float) overall);
                oneRow.setRating_ratingString(overall);

                allRows.add(oneRow); // Adds each row to the list of rows to be added to the listView below
            }
        }else {
            myListView.setVisibility(View.INVISIBLE);
            TextView textView_errorMain = returnView.findViewById(R.id.textView_errorMain);
            textView_errorMain.setVisibility(View.VISIBLE);
            textView_errorMain.setText(R.string.error_friends);
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
        OverallCalculator overallCalculator;

        if (TripList.size() > 0) {
            for (int i = 0; i < TripList.size(); i++) {
                overallCalculator = new OverallCalculator(TripList.get(i).getScores());
                double overall = overallCalculator.overallRating;

                ListView_ItemNormal oneRow = new ListView_ItemNormal(); // Creates a new row
                // Below assigns values to their corresponding components
                oneRow.setImage_ratingImage(new ImageCalculator(overall).image);
                oneRow.setText_nameDay(TripList.get(i).getSlang_time());
                oneRow.setRating_ratingStars(overall);
                oneRow.setRating_ratingString(overall);

                allRows.add(oneRow); // Adds each row to the list of rows
            }
        }else {
            myListView.setVisibility(View.INVISIBLE);
            TextView textView_errorMain = returnView.findViewById(R.id.textView_errorMain);
            textView_errorMain.setVisibility(View.VISIBLE);
            textView_errorMain.setText(R.string.error_journeys);
        }

        // The journeys_adapter is is for adding all the user's journeys to the listView
        My_Adapter_JourneysMain journeys_adapter = new My_Adapter_JourneysMain(getContext(), allRows);
        myListView.setAdapter(journeys_adapter);
    }

    // Displays the welcome message with the user's name to the user
    private void setWelcomeTextRating(){
        TextView textView_welcome = returnView.findViewById(R.id.textView_welcome);
        textView_welcome.setText(String.format(WELCOME_MESSAGE, users_name));

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
        driving.putExtra("api",loggedIn_user.api);
        startActivity(driving);
    }


}
