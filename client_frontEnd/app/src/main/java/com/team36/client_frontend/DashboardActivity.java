package com.team36.client_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {
    final String welcome_dashboard = "%s's Dash";

    final String users_name = MainActivity.users_name;
    final double users_rating = MainActivity.users_rating;

    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener myBottomNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        // The below code transitions between activities
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Variable 'options' is as stated in MainActivity
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                    (DashboardActivity.this, navigation, "bottomNavView");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home = new Intent(DashboardActivity.this, MainActivity.class);
                    startActivity(home, options.toBundle());
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_allJourneys:
                    Intent allJourneys = new Intent(DashboardActivity.this, JourneysActivity.class);
                    startActivity(allJourneys, options.toBundle());
                    return true;
                case R.id.navigation_allFriends:
                    Intent allFriends = new Intent(DashboardActivity.this, FriendsActivity.class);
                    startActivity(allFriends, options.toBundle());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        // Gets the bottom navigation bar for the transition between activities
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(myBottomNavigationListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        myMain();
    }

    private void myMain(){
        TextView textView_welcome = findViewById(R.id.textView_welcomeDashboard);
        textView_welcome.setText(String.format(welcome_dashboard, users_name));

        RatingBar ratingBar_dashboard = findViewById(R.id.ratingBar_dashboardOverall);
        ratingBar_dashboard.setRating((float)users_rating);
    }
}
