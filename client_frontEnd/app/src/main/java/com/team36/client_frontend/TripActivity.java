package com.team36.client_frontend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TripActivity extends AppCompatActivity implements ServerResponded {
    private String journeyDate;
    private double[] journeysRatings;
    private LoggedIn_User loggedIn_user = new LoggedIn_User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);


        Bundle data = getIntent().getExtras();
        String trip = data.getString("Id");
        String toSend = ("api_key="+loggedIn_user.api+"&trip_id="+trip);
        //Send to the server
        new ServerSender(TripActivity.this).execute(toSend, "http://social-box.xyz/api/get_trip_detail", "");

        //String overall = String.valueOf(new OverallCalculator(journeysRatings).overallRating);
        //new OpenFriendJourneyFragment(null, new JourneyFragment(), getSupportFragmentManager(), journeyDate, Float.valueOf(overall), String.valueOf(journeysRatings[0]), String.valueOf(journeysRatings[1]), String.valueOf(journeysRatings[2]), String.valueOf(journeysRatings[3]));
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

    public void closePressed(View view){
        finish();
    }


    private void HandleResponce(String result) throws JSONException {
        JSONObject json = new JSONObject(result);
        journeyDate = json.getString("slang_time");
        JSONArray scores = json.getJSONArray("score");
        journeysRatings[0] = scores.getDouble(0);
        journeysRatings[1] = scores.getDouble(1);
        journeysRatings[2] = scores.getDouble(2);
        journeysRatings[3] = scores.getDouble(3);

        String overall = String.valueOf(new OverallCalculator(journeysRatings).overallRating);
        new OpenFriendJourneyFragment(null, new JourneyFragment(), getSupportFragmentManager(), journeyDate, Float.valueOf(overall), String.valueOf(journeysRatings[0]), String.valueOf(journeysRatings[1]), String.valueOf(journeysRatings[2]), String.valueOf(journeysRatings[3]));
    }

    @Override
    public void onTaskComplete(String result) {
        try {
            HandleResponce(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
