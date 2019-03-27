package com.team36.client_frontend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Fragment prevFragment;

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
                    setTitle(mainFragment.TITLE);
                    displayFragment(mainFragment);
                    return true;
                case R.id.navigation_dashboard:
                    // Transitions to the dashboard fragment, if applicable
                    DashboardFragment dashboardFragment = new DashboardFragment();
                    setTitle(dashboardFragment.TITLE);
                    displayFragment(dashboardFragment);
                    return true;
                case R.id.navigation_allJourneys:
                    // Transitions to the journeys fragment, if applicable
                    JourneysFragment journeysFragment = new JourneysFragment();
                    setTitle(journeysFragment.TITLE);
                    displayFragment(journeysFragment);
                    return true;
                case R.id.navigation_allFriends:
                    // Transitions to the friends fragment, if applicable
                    FriendsFragment friendsFragment = new FriendsFragment();
                    setTitle(friendsFragment.TITLE);
                    displayFragment(friendsFragment);
                    return true;
            }
            return false;
        }
    };

    private void displayFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction;
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_layout) != null) {
            getSupportFragmentManager().beginTransaction().remove(prevFragment).commit();
        }

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
        prevFragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        fragmentManager = getSupportFragmentManager();
        prevFragment = fragmentManager.findFragmentById(R.id.fragment_layout);

        MainFragment mainFragment = new MainFragment();
        displayFragment(mainFragment);
        setTitle(mainFragment.TITLE);

        BottomNavigationView navigation_base = findViewById(R.id.navigation_base);
        navigation_base.setOnNavigationItemSelectedListener(myBottomNavigationListener);
        // The navigation listener above 'listens' or detects when the user wants to change activity
        // and calls the 'myBottomNavigationListener' method when the user chooses

    }

}