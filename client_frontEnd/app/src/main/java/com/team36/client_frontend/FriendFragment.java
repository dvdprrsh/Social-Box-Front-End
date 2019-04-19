package com.team36.client_frontend;
// David Parrish - 201232252

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendFragment extends Fragment {
    private final String WELCOME_MESSAGE = "%s's Dashboard";
    private View returnView;

    private String[] journey_dates = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
    private double[] journey_ratings = {3.0, 4.5, 4.5, 3.5, 5.0, 4.0};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_friend, container, false);

        Bundle arguments = getArguments();
        ReturnView setView = new ReturnView(returnView, WELCOME_MESSAGE, arguments);
        friend_journeys();
        return setView.returnView;
    }

    // This method changes the listView to display basic information about the friend's journeys
    private void friend_journeys(){
        if (new NetworkAvailable(getActivity()).netAvailable()) {

            ArrayList<ListView_ItemNormal> allRows = new ArrayList<>(); // As stated previously
            ListView myListView = returnView.findViewById(R.id.listView_friendJourneys);

            if (journey_dates != null) {
                for (int i = 0; i < journey_dates.length; i++) {
                    ListView_ItemNormal oneRow = new ListView_ItemNormal(); // Creates a new row
                    // Below assigns values to their corresponding components
                    oneRow.setImage_ratingImage(new ImageCalculator(journey_ratings[i]).image);
                    oneRow.setText_nameDay(journey_dates[i]);
                    oneRow.setRating_ratingStars(journey_ratings[i]);
                    oneRow.setRating_ratingString(journey_ratings[i]);

                    allRows.add(oneRow); // Adds each row to the list of rows
                }
            }

            // The journeys_adapter is is for adding all the user's journeys to the listView
            My_Adapter_JourneysMain journeys_adapter = new My_Adapter_JourneysMain(getContext(), allRows);
            myListView.setAdapter(journeys_adapter);
        }
    }
}
