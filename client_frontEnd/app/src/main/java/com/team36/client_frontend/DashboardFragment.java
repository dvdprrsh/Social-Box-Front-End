package com.team36.client_frontend;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DashboardFragment extends Fragment {
    // These constants are used to store the values of each of the RatingBars and ImageViews of each rating section
    private final int[] RATING_BARS = {R.id.ratingBar_acceleration, R.id.ratingBar_braking, R.id.ratingBar_speed, R.id.ratingBar_time};
    private final int[] IMAGE_VIEWS = {R.id.imageView_acceleration, R.id.imageView_braking, R.id.imageView_speed, R.id.imageView_time};

    public final String TITLE = "Your Dashboard";
    private final String WELCOME_MESSAGE = "%s's Dashboard";

    private View returnView;
    private String USERS_NAME;
    private double USERS_RATING;
    private double[] RATINGS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Sets the key values used throughout this fragment
        LoggedIn_User loggedIn_user = new LoggedIn_User();
        USERS_NAME = loggedIn_user.user_firstName;
        USERS_RATING = loggedIn_user.user_overall;
        RATINGS = loggedIn_user.user_ratings;

        myMain();
        return returnView;
    }

    private void myMain(){
        TextView textView_welcome = returnView.findViewById(R.id.textView_welcomeDashboard);
        textView_welcome.setText(String.format(WELCOME_MESSAGE, USERS_NAME));

        RatingBar ratingBar_dashboard = returnView.findViewById(R.id.ratingBar_dashboardOverall);
        ratingBar_dashboard.setRating((float)USERS_RATING); // Sets the overall rating

        displayStatistics();
    }

    // Displays the statistics for each card view
    private void displayStatistics(){
        for (int i=0; i<RATING_BARS.length; i++){
            RatingBar ratingBar = returnView.findViewById(RATING_BARS[i]);
            ratingBar.setRating((float)RATINGS[i]);

            // Gets the image that the rating corresponds to
            ImageCalculator imageCalculator = new ImageCalculator(RATINGS[i]);

            ImageView imageView = returnView.findViewById(IMAGE_VIEWS[i]);
            imageView.setImageResource(imageCalculator.image);
        }
    }
}
