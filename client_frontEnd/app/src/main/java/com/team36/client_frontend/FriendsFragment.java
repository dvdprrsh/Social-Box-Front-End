package com.team36.client_frontend;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class FriendsFragment extends Fragment {
    private String[] friendNames = {"Cam", "Cybs", "Dave", "George", "Javier", "Josh"};
    private double[][] friendRatings = {{4.5, 4.0, 5.0, 5.0}, {3.0, 4.0, 4.0, 3.5}, {4.5, 4.0, 4.0, 3.5}, {5.0, 4.0, 4.0, 3.5}, {4.0, 4.0, 4.5, 3.5}, {3.5, 3.5, 4.0, 4.0}};

    public ListView.OnItemClickListener myItemClickListener = new ListView.OnItemClickListener(){

        // This method is called when one of the journeys has been clicked/tapped
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            FriendFragment friendFragment = new FriendFragment();
            Bundle arguments = new Bundle(); // For passing data across to the 'journeyFragment' fragment

            // Finds all the views which data needs to be passed across to 'journeyFragment'
            TextView name = view.findViewById(R.id.textView_dayDate);
            RatingBar ratingOverall = view.findViewById(R.id.ratingBar_overallStars);
            ImageView ratingAcceleration = view.findViewById(R.id.imageView_acceleration);
            ImageView ratingBraking = view.findViewById(R.id.imageView_braking);
            ImageView ratingSpeed = view.findViewById(R.id.imageView_speed);
            ImageView ratingTime = view.findViewById(R.id.imageView_time);

            // Adds values to the 'arguments' bundle so that the data stored in it can be used
            arguments.putString("dateName", name.getText().toString());
            arguments.putFloat("ratingOverall", ratingOverall.getRating());
            arguments.putString("ratingAcceleration", ratingAcceleration.getTag().toString());
            arguments.putString("ratingBraking", ratingBraking.getTag().toString());
            arguments.putString("ratingSpeed", ratingSpeed.getTag().toString());
            arguments.putString("ratingTime", ratingTime.getTag().toString());

            friendFragment.setArguments(arguments); // Sets the arguments for the fragment

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Assigns values to the fragment Transaction
            fragmentTransaction
                    .replace(R.id.fragment_layout, friendFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (fragmentManager.getBackStackEntryCount() < 1){
                fragmentTransaction.addToBackStack(null); // To prevent multiple 'back' button presses
            }
            fragmentTransaction.commit();
        }
    };

    public View.OnClickListener fabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AddPendingFriendsActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View returnView = inflater.inflate(R.layout.fragment_journeysfriends_list, container, false);

        TextView textView_welcomeText = returnView.findViewById(R.id.textView_welcome);
        textView_welcomeText.setText(R.string.friends_welcome);

        ListView listView = returnView.findViewById(R.id.listView_journeysFriends);

        if (friendNames != null) {
            listView.setOnItemClickListener(myItemClickListener);
            LoadList loadList = new LoadList(friendNames, friendRatings);
            // The below displays all the rows made in the 'loadList' class above
            My_Adapter_JourneysFriends friendsList_myAdapter = new My_Adapter_JourneysFriends(getContext(), loadList.allRows);
            listView.setAdapter(friendsList_myAdapter);
        }else{
            listView.setVisibility(View.INVISIBLE);
            TextView textView_error = returnView.findViewById(R.id.textView_error);
            textView_error.setVisibility(View.VISIBLE);
            textView_error.setText(R.string.error_friends);
        }

        FloatingActionButton addFriend = returnView.findViewById(R.id.fab_add);
        addFriend.show();
        addFriend.setOnClickListener(fabClick);

        return returnView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView(); // Destroys the fragment when called
    }




}
