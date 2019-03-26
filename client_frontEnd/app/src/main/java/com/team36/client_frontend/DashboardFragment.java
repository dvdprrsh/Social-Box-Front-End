package com.team36.client_frontend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

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
        returnView = inflater.inflate(R.layout.card_dashboard, container, false);

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

            ImageView imageView = returnView.findViewById(IMAGE_VIEWS[i]);
            setImage(RATINGS[i], imageView);
        }
    }

    // Sets the image for each card view dependant on the rating of each section rating
    private void setImage(Double sectionRating, ImageView imageView){
        switch (Double.toString(sectionRating)){
            case "1.0":
            case "1.5":
                imageView.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_48dp);
                break;

            case "2.0":
            case "2.5":
                imageView.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_48dp);
                break;

            case "3.0":
                imageView.setImageResource(R.drawable.ic_sentiment_neutral_black_48dp);
                break;

            case "3.5":
                imageView.setImageResource(R.drawable.ic_sentiment_neutral_black_48dp);
                break;

            case "4.0":
                imageView.setImageResource(R.drawable.ic_sentiment_satisfied_black_48dp);
                break;

            case "4.5":
                imageView.setImageResource(R.drawable.ic_sentiment_very_satisfied_black_48dp);
                break;

            case "5.0":
                imageView.setImageResource(R.drawable.ic_whatshot_black_48dp);
        }
    }
}
