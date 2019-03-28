package com.team36.client_frontend;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuPresenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class JourneysFragment extends Fragment {
    public final String TITLE = "Your Journeys";
    private View returnView;
    private final int RATING_ACCELERATION = 0, RATING_BRAKING = 1, RATING_SPEED = 2, RATING_TIME = 3;

    private String[] journey_dates = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
    private double[][] journeysRatings = {{3.0, 4.0, 4.0, 3.5}, {4.5, 4.0, 5.0, 5.0}, {4.5, 4.0, 4.0, 3.5}, {3.5, 3.5, 4.0, 4.0}, {5.0, 4.0, 4.0, 3.5}, {4.0, 4.0, 4.5, 3.5}};


    // TODO: Add a more detailed welcome message, e.g. "*NAME*'s Journeys"
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_journeys, container, false);
        loadList();
        return returnView;
    }

    private ListView.OnItemClickListener myItemClickListener = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    private void loadList(){

        ArrayList<listView_itemJourneys> allRows = new ArrayList<>();
        ListView listView = returnView.findViewById((R.id.listView_journeys));
        listView.setOnItemClickListener(myItemClickListener);

        if (journey_dates != null){
            for (int i=0; i<journey_dates.length; i++){
                listView_itemJourneys oneRow = new listView_itemJourneys();
                double journeyOverall = new OverallCalculator(journeysRatings[i]).overallRating;
                ImageCalculator imageCalculator = new ImageCalculator(journeyOverall);

                oneRow.setImage_ratingImage(imageCalculator.image);
                oneRow.setText_nameDay(journey_dates[i]);
                oneRow.setRating_ratingStars(journeyOverall);
                oneRow.setRating_ratingString(journeyOverall);
                oneRow.setImage_ratingAcceleration(imageCalculator.setImage(journeysRatings[i][RATING_ACCELERATION]));
                oneRow.setImage_ratingBraking(imageCalculator.setImage(journeysRatings[i][RATING_BRAKING]));
                oneRow.setImage_ratingSpeed(imageCalculator.setImage(journeysRatings[i][RATING_SPEED]));
                oneRow.setImage_ratingTime(imageCalculator.setImage(journeysRatings[i][RATING_TIME]));

                allRows.add(oneRow);
            }
            myAdapter_journeysList journeysList_myAdapter = new myAdapter_journeysList(getContext(), allRows);
            listView.setAdapter(journeysList_myAdapter);
        }
    }

}
