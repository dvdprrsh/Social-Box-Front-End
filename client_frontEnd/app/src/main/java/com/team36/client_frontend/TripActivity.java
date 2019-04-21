package com.team36.client_frontend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TripActivity extends AppCompatActivity {
    private String journeyDate = "Monday";
    private double[] journeysRatings = {3.0, 4.0, 4.0, 3.5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        String overall = String.valueOf(new OverallCalculator(journeysRatings).overallRating);
        new OpenFriendJourneyFragment(null, new JourneyFragment(), getSupportFragmentManager(), journeyDate, Float.valueOf(overall), String.valueOf(journeysRatings[0]), String.valueOf(journeysRatings[1]), String.valueOf(journeysRatings[2]), String.valueOf(journeysRatings[3]));
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

    public void closePressed(View view){
        finish();
    }
}
