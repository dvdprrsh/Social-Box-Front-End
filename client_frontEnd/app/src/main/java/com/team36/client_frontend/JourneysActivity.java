package com.team36.client_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class JourneysActivity extends AppCompatActivity {
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener myBottomNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(JourneysActivity.this, navigation, "bottomNavView");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home = new Intent(JourneysActivity.this, MainActivity.class);
                    startActivity(home, options.toBundle());
                    return true;
                case R.id.navigation_dashboard:
                    Intent dashboard = new Intent(JourneysActivity.this, MainActivity.class);
                    startActivity(dashboard, options.toBundle());
                    return true;
                case R.id.navigation_allJourneys:
                    return true;
                case R.id.navigation_allFriends:
                    Intent allFriends = new Intent(JourneysActivity.this, FriendsActivity.class);
                    startActivity(allFriends, options.toBundle());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journeys);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(myBottomNavigationListener);
        navigation.setSelectedItemId(R.id.navigation_allJourneys);
    }
}
