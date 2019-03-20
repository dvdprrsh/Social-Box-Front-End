package com.team36.client_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class DashboardActivity extends AppCompatActivity {
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener myBottomNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(DashboardActivity.this, navigation, "bottomNavView");
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

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(myBottomNavigationListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

    }
}
