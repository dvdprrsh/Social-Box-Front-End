package com.team36.client_frontend;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class JourneysFragment extends Fragment {
    public final String TITLE = "Your Journeys";
    private final int RATING_ACCELERATION = 0, RATING_BRAKING = 1, RATING_SPEED = 2, RATING_TIME = 3;

    private View returnView;
    private String[] journey_dates = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
    private double[][] journeysRatings = {{3.0, 4.0, 4.0, 3.5}, {4.5, 4.0, 5.0, 5.0}, {4.5, 4.0, 4.0, 3.5}, {3.5, 3.5, 4.0, 4.0}, {5.0, 4.0, 4.0, 3.5}, {4.0, 4.0, 4.5, 3.5}};

    // TODO: Add a more detailed welcome message, e.g. "*NAME*'s Journeys"
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_journeys, container, false);
        loadList(); // Loads the list of journeys

        return returnView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView(); // Destroys the fragment when called
    }

    private ListView.OnItemClickListener myItemClickListener = new ListView.OnItemClickListener(){

        // This method is called when one of the journeys has been clicked/tapped
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            JourneyFragment journeyFragment = new JourneyFragment();
            Bundle arguments = new Bundle(); // For passing data across to the 'journeyFragment' fragment

            // Finds all the views which data needs to be passed across to 'journeyFragment'
            TextView dayDate = view.findViewById(R.id.textView_dayDate);
            RatingBar ratingOverall = view.findViewById(R.id.ratingBar_overallStars);
            ImageView ratingAcceleration = view.findViewById(R.id.imageView_acceleration);
            ImageView ratingBraking = view.findViewById(R.id.imageView_braking);
            ImageView ratingSpeed = view.findViewById(R.id.imageView_speed);
            ImageView ratingTime = view.findViewById(R.id.imageView_time);

            // Adds values to the 'arguments' bundle so that the data stored in it can be used
            arguments.putString("dayDate", dayDate.getText().toString());
            arguments.putFloat("ratingOverall", ratingOverall.getRating());
            arguments.putString("ratingAcceleration", ratingAcceleration.getTag().toString());
            arguments.putString("ratingBraking", ratingBraking.getTag().toString());
            arguments.putString("ratingSpeed", ratingSpeed.getTag().toString());
            arguments.putString("ratingTime", ratingTime.getTag().toString());

            journeyFragment.setArguments(arguments); // Sets the arguments for the fragment

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Assigns values to the fragment Transaction
            fragmentTransaction
                    .replace(R.id.fragment_layout, journeyFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (fragmentManager.getBackStackEntryCount() < 1){
                fragmentTransaction.addToBackStack(null); // To prevent multiple 'back' button presses
            }
            fragmentTransaction.commit();
        }
    };

    // Loads all the journeys the user has taken into a list
    private void loadList(){
        ArrayList<listView_itemJourneys> allRows = new ArrayList<>(); // Stores all the rows (each journey) of the list
        ListView listView = returnView.findViewById((R.id.listView_journeys));
        listView.setOnItemClickListener(myItemClickListener);

        // Assigns values to to each of the components of each row/journey of the list if the user has taken one or more journeys
        if (journey_dates != null){
            // Loops through each of the journeys
            for (int i=0; i<journey_dates.length; i++){
                listView_itemJourneys oneRow = new listView_itemJourneys();
                double journeyOverall = new OverallCalculator(journeysRatings[i]).overallRating;
                ImageCalculator imageCalculator = new ImageCalculator(journeyOverall);

                Double accelerationVal = journeysRatings[i][RATING_ACCELERATION];
                Double brakeVal = journeysRatings[i][RATING_BRAKING];
                Double speedVal = journeysRatings[i][RATING_SPEED];
                Double timeVal = journeysRatings[i][RATING_TIME];

                oneRow.setImage_ratingImage(imageCalculator.image);
                oneRow.setText_nameDay(journey_dates[i]);
                oneRow.setRating_ratingStars(journeyOverall);
                oneRow.setRating_ratingString(journeyOverall);
                oneRow.setImage_ratingAcceleration(imageCalculator.setImage(accelerationVal), accelerationVal);
                oneRow.setImage_ratingBraking(imageCalculator.setImage(brakeVal), brakeVal);
                oneRow.setImage_ratingSpeed(imageCalculator.setImage(speedVal), speedVal);
                oneRow.setImage_ratingTime(imageCalculator.setImage(timeVal), timeVal);

                allRows.add(oneRow);
            }

            // Displays all the rows made above
            myAdapter_journeysList journeysList_myAdapter = new myAdapter_journeysList(getContext(), allRows);
            listView.setAdapter(journeysList_myAdapter);
        }
    }

}
