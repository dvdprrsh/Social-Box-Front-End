package com.team36.client_frontend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TripActivity extends AppCompatActivity implements ServerResponded {
    private String journeyDate;
    private int[] journeysRatings = new int[4];
    private double[] doubleRatings;
    private String api;
    private JSONObject jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);


        Bundle data = getIntent().getExtras();
        String trip = data.getString("Id");
        api = data.getString("api");
        String toSend = ("api_key="+api+"&trip_id="+trip);
        //Send to the server
        new ServerSender(TripActivity.this).execute(toSend, "http://social-box.xyz/api/get_trip_detail", "");
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
        JSONObject scores = json.getJSONObject("scores");
        journeysRatings[0] = scores.getInt("acceleration");
        journeysRatings[1] = scores.getInt("braking");
        journeysRatings[2] = scores.getInt("speeding");
        journeysRatings[3] = scores.getInt("time_of_day");

        doubleRatings = new StarCalculator(journeysRatings).starRatings;

        JSONArray lat = json.getJSONArray("lat");
        JSONArray longs = json.getJSONArray("long");
        double[] coords = new double[(lat.length() * 2)];
        int x = 0;
        for (int i = 0; i < coords.length; i++) {
            coords[i] = longs.getDouble(x);
            i++;
            coords[i] = lat.getDouble(x);
            x++;
        }


        String overall = String.valueOf(new OverallCalculator(doubleRatings).overallRating);
        new OpenFriendJourneyFragment(null, new JourneyFragment(), getSupportFragmentManager(), journeyDate, Float.valueOf(overall), String.valueOf(doubleRatings[0]), String.valueOf(doubleRatings[1]), String.valueOf(doubleRatings[2]), String.valueOf(doubleRatings[3]), coords);
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
