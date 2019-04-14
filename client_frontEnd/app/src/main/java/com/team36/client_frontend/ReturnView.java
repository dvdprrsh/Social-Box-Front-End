package com.team36.client_frontend;
// David Parrish - 201232252

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import static java.lang.Float.parseFloat;

public class ReturnView {
    private final int[] VIEWS = {R.id.textView_welcomeJF, R.id.ratingBar_barOverall, R.id.textView_overallRating,
            R.id.ratingBar_acceleration, R.id.imageView_acceleration, R.id.ratingBar_braking, R.id.imageView_braking,
            R.id.ratingBar_speed, R.id.imageView_speed, R.id.ratingBar_time, R.id.imageView_time};

    // This array contains the keys for all the arguments in the Arguments bundle
    private final String[] KEYS = {"dateName", "ratingOverall", "ratingAcceleration", "ratingBraking", "ratingSpeed", "ratingTime"};

    public View returnView;

    ReturnView(View returnView, String WELCOME_MESSAGE, Bundle arguments){
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
        this.returnView = returnView;
    }
}
