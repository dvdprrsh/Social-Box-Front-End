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

public class BaseActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private BottomNavigationView navigation_base;
    public LoggedIn_User loggedIn_user;
    private boolean netAvailable;
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

        MainFragment mainFragment = new MainFragment();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, mainFragment).commit();

        navigation_base = findViewById(R.id.navigation_base);
        navigation_base.setOnNavigationItemSelectedListener(myBottomNavigationListener);

        Bundle data = getIntent().getExtras();
        String json = data.getString("json");
        try {
            fillUser(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        double[] rating = {};
        JSONArray friendIds = userData.getJSONArray("friends");
        String[] frinds = new String[friendIds.length()];

        for (int i = 0; i < friendIds.length(); i++) {
            frinds[i] = (String)(friendIds.get(i));
        }
        loggedIn_user = new LoggedIn_User(api,firstname,surname,email,username,rating,frinds);
    }

}
