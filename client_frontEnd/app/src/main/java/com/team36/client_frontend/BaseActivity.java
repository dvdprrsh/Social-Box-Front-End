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

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private BottomNavigationView navigation_base;

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
                    if (!(sameSelected(R.id.navigation_home))) {
                        MainFragment mainFragment = new MainFragment();
                        displayFragment(mainFragment);
                    }
                    return true;
                case R.id.navigation_allJourneys:
                    // Transitions to the journeys fragment, if applicable
                    if (!(sameSelected(R.id.navigation_allJourneys))) {
                        JourneysFragment journeysFragment = new JourneysFragment();
                        displayFragment(journeysFragment);
                    }
                    return true;
                case R.id.navigation_allFriends:
                    // Transitions to the friends fragment, if applicable
                    if (!(sameSelected(R.id.navigation_allFriends))) {
                        FriendsFragment friendsFragment = new FriendsFragment();
                        displayFragment(friendsFragment);
                    }
                    return true;
                case R.id.navigation_profile:
                    // Transitions to the dashboard fragment, if applicable
                    if (!(sameSelected(R.id.navigation_profile))) {
                        ProfileFragment profileFragment = new ProfileFragment();
                        displayFragment(profileFragment);
                    }
                    return true;
            }
            return false;
        }
    };

    private boolean sameSelected(int selectedItem){
        return navigation_base.getSelectedItemId() == selectedItem;
    }

    // Displays the given fragment in the frame layout 'fragment_layout'
    private void displayFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction;
        // Removes all previous fragments from the back stack
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++){
            fragmentManager.popBackStackImmediate();
        }

        Fragment f = fragmentManager.findFragmentByTag("prevFragment");
        if (f != null) fragmentManager.beginTransaction().remove(f).commit();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(R.id.fragment_layout, fragment, "prevFragment");
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        fragmentManager = getSupportFragmentManager();

        MainFragment mainFragment = new MainFragment();
        displayFragment(mainFragment);

        navigation_base = findViewById(R.id.navigation_base);
        navigation_base.setOnNavigationItemSelectedListener(myBottomNavigationListener);
        // The navigation listener above 'listens' or detects when the user wants to change activity
        // and calls the 'myBottomNavigationListener' method when the user chooses
    }

}
