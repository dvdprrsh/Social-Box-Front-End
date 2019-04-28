package com.team36.client_frontend;
// David Parrish - 201232252

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JourneysFragment extends Fragment {
    private final String WELCOME_TEXT = "Your Journeys";
    private List<TripInformation> tripList;
    private JSONObject json;
    private JSONArray jsonTrips;
    private String[] journeyDates;
    private double[][] journeysRatings;

    public ListView.OnItemClickListener myItemClickListener = new ListView.OnItemClickListener(){

        // This method is called when one of the journeys has been clicked/tapped
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            JourneyFragment journeyFragment = new JourneyFragment();

            double[] coords = {};
            try {
                coords = getCoords(((tripList.size()-1) - position));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Finds all the views which data needs to be passed across to 'journeyFragment'
            TextView date = view.findViewById(R.id.textView_dayDate);
            RatingBar rating0 = view.findViewById(R.id.ratingBar_overallStars);
            ImageView rating1 = view.findViewById(R.id.imageView_acceleration);
            ImageView rating2 = view.findViewById(R.id.imageView_braking);
            ImageView rating3 = view.findViewById(R.id.imageView_speed);
            ImageView rating4 = view.findViewById(R.id.imageView_time);

            String journeyDate = date.getText().toString();
            Float ratingOverall = rating0.getRating();
            String ratingAcceleration = rating1.getTag().toString();
            String ratingBraking = rating2.getTag().toString();
            String ratingSpeed = rating3.getTag().toString();
            String ratingTime = rating4.getTag().toString();

            // opens the selected journey's fragment
            new OpenFriendJourneyFragment(null, journeyFragment, getActivity().getSupportFragmentManager(),
                    journeyDate, ratingOverall, ratingAcceleration, ratingBraking, ratingSpeed, ratingTime, coords);
        }
    };

    private double[] getCoords(int position) throws JSONException {

        JSONObject trip = (jsonTrips.getJSONObject(position));
        JSONArray lat = trip.getJSONArray("lat");
        JSONArray longs = trip.getJSONArray("long");
        double[] coords = new double[(lat.length() * 2)];
        int x = 0;
        for (int i = 0; i < coords.length; i++) {
            coords[i] = longs.getDouble(x);
            i++;
            coords[i] = lat.getDouble(x);
            x++;
        }

        return coords;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View returnView = inflater.inflate(R.layout.fragment_journeysfriends_list, container, false);
        TextView textView_welcomeText = returnView.findViewById(R.id.textView_welcomeJsFs);
        textView_welcomeText.setText(WELCOME_TEXT);

        ListView listView = returnView.findViewById((R.id.listView_journeysFriends));

        BaseActivity baseActivity = (BaseActivity) getActivity();
        tripList = baseActivity.TripList;
        try {
            json = baseActivity.josn;
            jsonTrips = json.getJSONArray("trips");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        journeyDates = new String[tripList.size()];
        journeysRatings = new double[tripList.size()][4];

        for (int i = 0; i < tripList.size(); i++) {
            journeyDates[i] = tripList.get(i).getSlang_time();
            journeysRatings[i] = tripList.get(i).getScores();
        }

        if (new NetworkAvailable(getActivity()).netAvailable()) {

            if (journeyDates != null) {
                listView.setOnItemClickListener(myItemClickListener);
                LoadList loadList = new LoadList(journeyDates, journeysRatings);
                // The below displays all the rows made in the 'loadList' class above
                My_Adapter_JourneysFriends journeysList_myAdapter = new My_Adapter_JourneysFriends(getContext(), loadList.allRows);
                listView.setAdapter(journeysList_myAdapter);
            } else {
                // Displays a message to the user if they have not taken any journeys yet
                listView.setVisibility(View.INVISIBLE);
                TextView textView_error = returnView.findViewById(R.id.textView_error);
                textView_error.setVisibility(View.VISIBLE);
                textView_error.setText(R.string.error_journeys);
            }

        }
        return returnView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView(); // Destroys the fragment when called
    }




}
