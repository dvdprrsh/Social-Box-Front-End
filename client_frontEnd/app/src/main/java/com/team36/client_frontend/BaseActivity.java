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
                    MainFragment mainFragment = new MainFragment();
                    if (!(sameSelected(R.id.navigation_home, mainFragment))) { // Prevents transition to same fragment
                        displayFragment(mainFragment);
                    }
                    return true;
                case R.id.navigation_allJourneys:
                    // Transitions to the journeys fragment, if applicable
                    JourneysFragment journeysFragment = new JourneysFragment();
                    if (!(sameSelected(R.id.navigation_allJourneys, journeysFragment))) { // Prevents transition to same fragment
                        displayFragment(journeysFragment);
                    }
                    return true;
                case R.id.navigation_allFriends:
                    // Transitions to the friends fragment, if applicable
                    FriendsFragment friendsFragment = new FriendsFragment();
                    if (!(sameSelected(R.id.navigation_allFriends, friendsFragment))) { // Prevents transition to same fragment
                        displayFragment(friendsFragment);
                    }
                    return true;
                case R.id.navigation_profile:
                    // Transitions to the dashboard fragment, if applicable
                    ProfileFragment profileFragment = new ProfileFragment();
                    if (!(sameSelected(R.id.navigation_profile, profileFragment))) { // Prevents transition to same fragment
                        displayFragment(profileFragment);
                    }
                    return true;
            }
            return false;
        }
    };

    /* This function prevents the user from pressing the same navigation button multiple times,
       this prevents the fragment from showing its displayed animation multiple times */
    private boolean sameSelected(int selectedItem, Fragment toDisplay){
        Fragment displayedFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
        Class c = displayedFragment.getClass();
        return (navigation_base.getSelectedItemId() == selectedItem) && (c == toDisplay.getClass());
    }

    // Displays the given fragment in the frame layout 'fragment_layout'
    private void displayFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction;
        // Removes all previous fragments from the back stack
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++){
            fragmentManager.popBackStackImmediate();
        }

        // The next two lines remove the previous fragment (if there is one) before the next fragment is displayed to prevent overlap of transition animations
        Fragment f = fragmentManager.findFragmentByTag("prevFragment");
        if (f != null) fragmentManager.beginTransaction().remove(f).commit();

        // Displays the next fragment
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                .add(R.id.fragment_layout, fragment, "prevFragment")
                .commit();
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
