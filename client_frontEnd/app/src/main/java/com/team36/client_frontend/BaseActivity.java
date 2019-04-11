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
    public static Fragment prevFragment;

    private int prevPos = 0;

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
                    displayFragment(mainFragment);
                    return true;
                case R.id.navigation_allJourneys:
                    // Transitions to the journeys fragment, if applicable
                    JourneysFragment journeysFragment = new JourneysFragment();
                    displayFragment(journeysFragment);
                    return true;
                case R.id.navigation_allFriends:
                    // Transitions to the friends fragment, if applicable
                    FriendsFragment friendsFragment = new FriendsFragment();
                    displayFragment(friendsFragment);
                    return true;
                case R.id.navigation_profile:
                    // Transitions to the dashboard fragment, if applicable
                    ProfileFragment profileFragment = new ProfileFragment();
                    displayFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };

    // Displays the given fragment in the frame layout 'fragment_layout'
    private void displayFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction;
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_layout) != null) {
            List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

            // Removes all previous fragments so they do not overlap each other
            for (Fragment f:fragmentList) {
                getSupportFragmentManager().beginTransaction().remove(f).commit();
            }
        }

        // Removes all previous fragments from the back stack to prevent overlap again
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++){
            fragmentManager.popBackStackImmediate();
        }

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
        prevFragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RemoveStatus(this);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_base);
        fragmentManager = getSupportFragmentManager();
        prevFragment = fragmentManager.findFragmentById(R.id.fragment_layout);

        MainFragment mainFragment = new MainFragment();
        displayFragment(mainFragment);

        BottomNavigationView navigation_base = findViewById(R.id.navigation_base);
        navigation_base.setOnNavigationItemSelectedListener(myBottomNavigationListener);
        // The navigation listener above 'listens' or detects when the user wants to change activity
        // and calls the 'myBottomNavigationListener' method when the user chooses
    }

}
