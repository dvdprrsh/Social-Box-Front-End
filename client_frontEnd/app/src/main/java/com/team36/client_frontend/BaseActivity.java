package com.team36.client_frontend;
// David Parrish - 201232252

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BaseActivity extends AppCompatActivity implements ServerResponded {
    private FragmentManager fragmentManager;
    private BottomNavigationView navigation_base;
    public LoggedIn_User loggedIn_user;
    public List<TripInformation> TripList = new ArrayList<>();
    public List<FriendInformation> FriendList = new ArrayList<>();
    private boolean netAvailable;
    public JSONObject josn;
    public JSONObject friendsjson;
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
                    // Transitions to the home/main fragment, if applicable
                    MainFragment mainFragment = new MainFragment();
                    if (!(sameSelected(R.id.navigation_home, mainFragment))) { // Prevents transition to same fragment
                        displayFragment(mainFragment);
                        return netAvailable;
                    }
                    return false;
                case R.id.navigation_allJourneys:
                    // Transitions to the journeys fragment, if applicable
                    JourneysFragment journeysFragment = new JourneysFragment();
                    if (!(sameSelected(R.id.navigation_allJourneys, journeysFragment))) { // Prevents transition to same fragment
                        displayFragment(journeysFragment);
                        return netAvailable;
                    }
                    return false;
                case R.id.navigation_allFriends:
                    // Transitions to the friends fragment, if applicable
                    FriendsFragment friendsFragment = new FriendsFragment();
                    if (!(sameSelected(R.id.navigation_allFriends, friendsFragment))) { // Prevents transition to same fragment
                        displayFragment(friendsFragment);
                        return netAvailable;
                    }
                    return false;
                case R.id.navigation_profile:
                    // Transitions to the dashboard fragment, if applicable
                    ProfileFragment profileFragment = new ProfileFragment();
                    if (!(sameSelected(R.id.navigation_profile, profileFragment))) { // Prevents transition to same fragment
                        displayFragment(profileFragment);
                        return netAvailable;
                    }
                    return false;
            }
            return false;
        }
    };

    /* This function prevents the user from pressing the same navigation button multiple times,
       this prevents the fragment from showing its displayed animation multiple times */
    private boolean sameSelected(int selectedItem, Fragment toDisplay){
        Class c = null;
        Fragment displayedFragment = fragmentManager.findFragmentById(R.id.fragment_layout);
        if (displayedFragment!=null) {
            c = displayedFragment.getClass();
        }
        return (navigation_base.getSelectedItemId() == selectedItem) && (c == toDisplay.getClass());
    }

    // Displays the given fragment in the frame layout 'fragment_layout'
    private void displayFragment(Fragment fragment){
        netAvailable = new NetworkAvailable(this).netAvailable();
        if (netAvailable) {
            FragmentTransaction fragmentTransaction;

            fragmentTransaction = fragmentManager.beginTransaction();
            // Displays the next fragment
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment_layout, fragment)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        fragmentManager = getSupportFragmentManager();

        navigation_base = findViewById(R.id.navigation_base);
        navigation_base.setOnNavigationItemSelectedListener(myBottomNavigationListener);

        Bundle data = getIntent().getExtras();
        String json = data.getString("json");
        try {
            fillUser(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String toSend = ("api_key="+loggedIn_user.api);
        //Send to the server
        new ServerSender(BaseActivity.this).execute(toSend, "http://social-box.xyz/api/get_all_trips", "");

        toSend = ("api_key="+loggedIn_user.api);
        new ServerSender(BaseActivity.this).execute(toSend, "http://social-box.xyz/api/get_friends", "");
        // The navigation listener above 'listens' or detects when the user wants to change activity
        // and calls the 'myBottomNavigationListener' method when the user chooses


    }

    private void fillUser (String json) throws JSONException {
        JSONObject userData = new JSONObject(json);
        String api = userData.getString("api_key");
        String email = userData.getString("email");
        String username = userData.getString("username");
        String firstname = userData.getString("firstname");
        String surname = userData.getString("surname");
        int[] ratings = new int[4];

        try {
            JSONObject scores = userData.getJSONObject("scores");
            ratings[0] = scores.getInt("acceleration");
            ratings[1] = scores.getInt("braking");
            ratings[2] = scores.getInt("speeding");
            ratings[3] = scores.getInt("time_of_day");
        } catch (Exception e) {
            ratings[0] = 0;
            ratings[1] = 0;
            ratings[2] = 0;
            ratings[3] = 0;
        }
        String[] friends;
        try {
            JSONArray friendIds = userData.getJSONArray("friends");
            friends = new String[friendIds.length()];

            for (int i = 0; i < friendIds.length(); i++) {
                friends[i] = (String)(friendIds.get(i));
            }
        } catch (JSONException e){
            friends = new String[0];
        }
        loggedIn_user = new LoggedIn_User(api,firstname,surname,email,username,ratings,friends);
    }

    @Override
    public void onTaskComplete(String result) {

        try {
            JSONObject json = new JSONObject(result);
            if(!json.has("friends")) {
                FillTrips(result);
            } else {
                FillFriends(result);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void FillTrips(String json) throws JSONException {
        JSONObject data = new JSONObject(json);
        josn = data;
        JSONArray trips = (data.getJSONArray("trips"));

        for(int i = 0; i < trips.length(); i++) {
            JSONObject trip = trips.getJSONObject(i);
            int[] scores;
            try {
                JSONObject JSONscores = trip.getJSONObject("scores");
                scores = new int[] {JSONscores.getInt("acceleration"),JSONscores.getInt("braking"),JSONscores.getInt("speeding"),JSONscores.getInt("time_of_day")};
            } catch (Exception e) {
                scores = new int[] {0,0,0,0};
            }


            TripInformation toAdd = new TripInformation(scores,trip.getString("slang_time"), "banging trip id");
            TripList.add(toAdd);
        }
        Collections.reverse(TripList);
        HandleTrips();
    }

    private void FillFriends(String jsonFriend) throws JSONException {
        JSONObject data = new JSONObject(jsonFriend);
        friendsjson = data;
        JSONArray friends = (data.getJSONArray("friends"));

        for(int i = 0; i < friends.length(); i++) {
            JSONObject friend = friends.getJSONObject(i);
            int[] scores;
            try {
                JSONObject JSONscores = friend.getJSONObject("scores");
                scores = new int[] {JSONscores.getInt("acceleration"),JSONscores.getInt("braking"),JSONscores.getInt("speeding"),JSONscores.getInt("time_of_day")};
            } catch (Exception e) {
                scores = new int[] {0,0,0,0};
            }
            FriendInformation toAdd = new FriendInformation(friend.getString("username"), scores);
            FriendList.add(toAdd);
        }

        HandleFriendJSON();
    }

    private void HandleTrips() {
        MainFragment mainFragment = new MainFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, mainFragment).commit();
    }

    private void HandleFriendJSON() {
        MainFragment mainFragment = new MainFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, mainFragment).commit();
    }

}
