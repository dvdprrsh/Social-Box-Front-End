package com.team36.client_frontend;
// David Parrish - 201232252

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class FriendsFragment extends Fragment {
    private final String WELCOME_TEXT = "Your Friends";
    private String[] friends;
    private LoggedIn_User loggedIn_user;
    private String[] friend_names = {"Cam", "Cybs", "Dave", "George", "Javier", "Josh"};
    private double[][] friend_ratings = {{4.5, 4.0, 5.0, 5.0}, {3.0, 4.0, 4.0, 3.5}, {4.5, 4.0, 4.0, 3.5}, {5.0, 4.0, 4.0, 3.5}, {4.0, 4.0, 4.5, 3.5}, {3.5, 3.5, 4.0, 4.0}};


    public ListView.OnItemClickListener myItemClickListener = new ListView.OnItemClickListener(){

        // This method is called when one of the journeys has been clicked/tapped
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FriendFragment friendFragment = new FriendFragment();

            // Finds all the views which data needs to be passed across to 'journeyFragment'
            TextView name = view.findViewById(R.id.textView_dayDate);
            RatingBar rating0 = view.findViewById(R.id.ratingBar_overallStars);
            ImageView rating1 = view.findViewById(R.id.imageView_acceleration);
            ImageView rating2 = view.findViewById(R.id.imageView_braking);
            ImageView rating3 = view.findViewById(R.id.imageView_speed);
            ImageView rating4 = view.findViewById(R.id.imageView_time);

            String friendName = name.getText().toString();
            Float ratingOverall = rating0.getRating();
            String ratingAcceleration = rating1.getTag().toString();
            String ratingBraking = rating2.getTag().toString();
            String ratingSpeed = rating3.getTag().toString();
            String ratingTime = rating4.getTag().toString();



            // Opens the selected friend's fragment
            new OpenFriendJourneyFragment(friendFragment, null, getActivity().getSupportFragmentManager(),
                    friendName, ratingOverall, ratingAcceleration, ratingBraking, ratingSpeed, ratingTime, null);
        }
    };

    public View.OnClickListener fabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Transitions ot the add friends and pending friend requests activity
            Intent intent = new Intent(getActivity(), AddPendingFriendsActivity.class);
            intent.putExtra("api", loggedIn_user.api);
            startActivity(intent);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View returnView = inflater.inflate(R.layout.fragment_journeysfriends_list, container, false);
        TextView textView_welcomeText = returnView.findViewById(R.id.textView_welcomeJsFs);
        textView_welcomeText.setText(WELCOME_TEXT);

        BaseActivity baseActivity = (BaseActivity) getActivity();
        loggedIn_user = baseActivity.loggedIn_user;
        friends = loggedIn_user.user_friendIDs;

        ListView listView = returnView.findViewById(R.id.listView_journeysFriends);

        if (friends.length > 0) {
            listView.setOnItemClickListener(myItemClickListener);
            //LoadList loadList = new LoadList(friend_names, friend_ratings);
            // The below displays all the rows made in the 'loadList' class above
            //My_Adapter_JourneysFriends friendsList_myAdapter = new My_Adapter_JourneysFriends(getContext(), loadList.allRows);
            //listView.setAdapter(friendsList_myAdapter);
        }else{
            listView.setVisibility(View.INVISIBLE);
            TextView textView_error = returnView.findViewById(R.id.textView_error);
            textView_error.setVisibility(View.VISIBLE);
            textView_error.setText(R.string.error_friends);
        }

        // Shows the add friend floating action button
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
