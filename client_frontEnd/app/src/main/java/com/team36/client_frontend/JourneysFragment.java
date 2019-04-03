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

public class JourneysFragment extends Fragment {
    public final String TITLE = "Your Journeys";

    private String[] journeyDates = null;//{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
    private double[][] journeysRatings = {{3.0, 4.0, 4.0, 3.5}, {4.5, 4.0, 5.0, 5.0}, {4.5, 4.0, 4.0, 3.5}, {3.5, 3.5, 4.0, 4.0}, {5.0, 4.0, 4.0, 3.5}, {4.0, 4.0, 4.5, 3.5}};

    // TODO: Add a more detailed welcome message, e.g. "*NAME*'s Journeys"
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View returnView = inflater.inflate(R.layout.fragment_journeysfriends_list, container, false);

        TextView textView_welcomeText = returnView.findViewById(R.id.textView_welcome);
        textView_welcomeText.setText(R.string.journeys_welcome);

        ListView listView = returnView.findViewById((R.id.listView_journeysFriends));

        if (journeyDates != null) {
            listView.setOnItemClickListener(myItemClickListener);
            LoadList loadList = new LoadList(journeyDates, journeysRatings);
            // The below displays all the rows made in the 'loadList' class above
            My_Adapter_JourneysFriends journeysList_myAdapter = new My_Adapter_JourneysFriends(getContext(), loadList.allRows);
            listView.setAdapter(journeysList_myAdapter);
        }else{
            listView.setVisibility(View.INVISIBLE);
            TextView textView_error = returnView.findViewById(R.id.textView_error);
            textView_error.setVisibility(View.VISIBLE);
            textView_error.setText(R.string.error_journeys);
        }
        return returnView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView(); // Destroys the fragment when called
    }

    public ListView.OnItemClickListener myItemClickListener = new ListView.OnItemClickListener(){

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
            arguments.putString("dateName", dayDate.getText().toString());
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

}
