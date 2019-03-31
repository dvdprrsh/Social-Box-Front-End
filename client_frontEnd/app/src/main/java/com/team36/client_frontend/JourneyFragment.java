package com.team36.client_frontend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.FormatFlagsConversionMismatchException;

import static java.lang.Float.parseFloat;

public class JourneyFragment extends Fragment {
    private final String WELCOME_MESSAGE = "%s's Journey";

    private final int[] VIEWS = {R.id.textView_welcomeJourney, R.id.ratingBar_journeyOverall, R.id.textView_overallRating,
            R.id.ratingBar_acceleration, R.id.imageView_acceleration, R.id.ratingBar_braking, R.id.imageView_braking,
            R.id.ratingBar_speed, R.id.imageView_speed, R.id.ratingBar_time, R.id.imageView_time};

    // This array contains the keys for all the arguments in the Arguments bundle
    private final String[] KEYS = {"dayDate", "ratingOverall", "ratingAcceleration", "ratingBraking", "ratingSpeed", "ratingTime"};

    private View returnView;
    private Bundle arguments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_journey, container, false);

        arguments = getArguments();
        setReturnView(); // Sets the components values to be displayed to the user

        return returnView;
    }

    private void setReturnView(){
        int viewCount = 0; // Keeps track of which view's value is being updated

        TextView textView = returnView.findViewById(VIEWS[viewCount++]); // Gets the view and increments 'viewCount' after it's value has been provided
        textView.setText(String.format(WELCOME_MESSAGE, arguments.getString(KEYS[0]))); // Sets the value of the welcome message

        float f = arguments.getFloat(KEYS[1]);
        RatingBar ratingBar = returnView.findViewById(VIEWS[viewCount++]);
        ratingBar.setRating(f);
        textView = returnView.findViewById(VIEWS[viewCount++]);
        textView.setText(Float.toString(f));

        // Loops through the rest of the components while assigning their values
        for (int i=2; i<KEYS.length; i++){
            f = parseFloat(arguments.getString(KEYS[i]));
            ImageCalculator imageCalculator = new ImageCalculator(f);
            ratingBar = returnView.findViewById(VIEWS[viewCount++]);
            ratingBar.setRating(f);
            ImageView imageView = returnView.findViewById(VIEWS[viewCount++]);
            imageView.setImageResource(imageCalculator.image);
        }
    }
}
