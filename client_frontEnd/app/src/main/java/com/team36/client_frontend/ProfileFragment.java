package com.team36.client_frontend;
// David Parrish - 201232252

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    // These constants are used to store the values of each of the RatingBars and ImageViews of each rating section
    private final int[] RATING_BARS = {R.id.ratingBar_acceleration, R.id.ratingBar_braking, R.id.ratingBar_speed, R.id.ratingBar_time};
    private final int[] IMAGE_VIEWS = {R.id.imageView_acceleration, R.id.imageView_braking, R.id.imageView_speed, R.id.imageView_time};

    private final String WELCOME_MESSAGE = "%s's Dashboard";

    private View returnView;
    private String USERS_NAME;
    private double USERS_RATING = -1.0;
    private double[] RATINGS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Sets the key values used throughout this fragment
        LoggedIn_User loggedIn_user = new LoggedIn_User();
        USERS_NAME = loggedIn_user.user_firstName;
        USERS_RATING = loggedIn_user.user_overall;
        RATINGS = loggedIn_user.user_ratings;

        Button button = returnView.findViewById(R.id.button_logout);
        button.setOnClickListener(this::onLogout);

        myMain();
        return returnView;
    }

    // When the logout button is pressed, this method is called
    public void onLogout(View view){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Logged_In", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", false); // This indicates the user has logged out so the app will display the login page when it is next opened
        editor.apply();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        // These flags below are used to disable the user pressing the back button to take them back to the profile fragment
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("checkLogin", false); // Indicates to the login activity not to check if the user is logged in
        startActivity(intent);
    }

    private void myMain(){
        TextView textView_welcome = returnView.findViewById(R.id.textView_welcomeDashboard);
        textView_welcome.setText(String.format(WELCOME_MESSAGE, USERS_NAME));

        RatingBar ratingBar_dashboard = returnView.findViewById(R.id.ratingBar_dashboardOverall);
        ratingBar_dashboard.setRating((float) USERS_RATING); // Sets the overall rating

        displayStatistics();
    }

    // Displays the statistics for each card view
    private void displayStatistics(){
        for (int i=0; i<RATING_BARS.length; i++){
            ImageView imageView = returnView.findViewById(IMAGE_VIEWS[i]);
            if (USERS_RATING != -1.0) {
                RatingBar ratingBar = returnView.findViewById(RATING_BARS[i]);
                ratingBar.setRating((float) RATINGS[i]);

                // Gets the image that the rating corresponds to
                ImageCalculator imageCalculator = new ImageCalculator(RATINGS[i]);
                imageView.setImageResource(imageCalculator.image);
            }else {
                imageView.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_48dp);
            }
        }
    }
}
