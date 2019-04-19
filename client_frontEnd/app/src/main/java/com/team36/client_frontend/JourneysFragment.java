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

public class JourneysFragment extends Fragment {
    private String[] journeyDates = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
    private double[][] journeysRatings = {{3.0, 4.0, 4.0, 3.5}, {4.5, 4.0, 5.0, 5.0}, {4.5, 4.0, 4.0, 3.5}, {3.5, 3.5, 4.0, 4.0}, {5.0, 4.0, 4.0, 3.5}, {4.0, 4.0, 4.5, 3.5}};

    public ListView.OnItemClickListener myItemClickListener = new ListView.OnItemClickListener(){

        // This method is called when one of the journeys has been clicked/tapped
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            JourneyFragment journeyFragment = new JourneyFragment();

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
                    journeyDate, ratingOverall, ratingAcceleration, ratingBraking, ratingSpeed, ratingTime);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View returnView = inflater.inflate(R.layout.fragment_journeysfriends_list, container, false);
        TextView textView_welcomeText = returnView.findViewById(R.id.textView_welcome);
        textView_welcomeText.setText(R.string.journeys_welcome);
        ListView listView = returnView.findViewById((R.id.listView_journeysFriends));

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
