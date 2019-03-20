package com.team36.client_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class FriendsActivity extends AppCompatActivity {
    BottomNavigationView navigation;

    // The method below does as stated in MainActivity.java
    private BottomNavigationView.OnNavigationItemSelectedListener myBottomNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(FriendsActivity.this, navigation, "bottomNavView");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home = new Intent(FriendsActivity.this, MainActivity.class);
                    startActivity(home, options.toBundle());
                    return true;
                case R.id.navigation_dashboard:
                    Intent dashboard = new Intent(FriendsActivity.this, DashboardActivity.class);
                    startActivity(dashboard, options.toBundle());
                    return true;
                case R.id.navigation_allJourneys:
                    Intent allJourneys = new Intent(FriendsActivity.this, JourneysActivity.class);
                    startActivity(allJourneys, options.toBundle());
                    return true;
                case R.id.navigation_allFriends:
                    // No transition required this activity is already being displayed
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        // Gets the bottom navigation bar for the transition between activities
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(myBottomNavigationListener);
        navigation.setSelectedItemId(R.id.navigation_allFriends);

    }
}
