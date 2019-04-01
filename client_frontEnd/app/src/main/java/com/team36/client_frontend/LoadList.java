package com.team36.client_frontend;
// David Parrish - 201232252

import java.util.ArrayList;

public class LoadList {
    private final int RATING_ACCELERATION = 0, RATING_BRAKING = 1, RATING_SPEED = 2, RATING_TIME = 3;
    public ArrayList<ListView_JourneysFriends> allRows = new ArrayList<>();
    private String[] datesNames;
    private double[][] ratings;

    LoadList(String[] datesNames, double[][] ratings){
        this.datesNames = datesNames;
        this.ratings = ratings;
        loadList();
    }

    // Loads all the journeys the user has taken into a list
    private void loadList(){// Stores all the rows (each journey) of the list

        // Assigns values to to each of the components of each row/journey of the list if the user has taken one or more journeys
        if (datesNames != null){
            // Loops through each of the journeys
            for (int i=0; i<datesNames.length; i++){
                ListView_JourneysFriends oneRow = new ListView_JourneysFriends();
                double journeyOverall = new OverallCalculator(ratings[i]).overallRating;
                ImageCalculator imageCalculator = new ImageCalculator(journeyOverall);

                Double accelerationVal = ratings[i][RATING_ACCELERATION];
                Double brakeVal = ratings[i][RATING_BRAKING];
                Double speedVal = ratings[i][RATING_SPEED];
                Double timeVal = ratings[i][RATING_TIME];

                oneRow.setImage_ratingImage(imageCalculator.image);
                oneRow.setText_nameDay(datesNames[i]);
                oneRow.setRating_ratingStars(journeyOverall);
                oneRow.setRating_ratingString(journeyOverall);
                oneRow.setImage_ratingAcceleration(imageCalculator.setImage(accelerationVal), accelerationVal);
                oneRow.setImage_ratingBraking(imageCalculator.setImage(brakeVal), brakeVal);
                oneRow.setImage_ratingSpeed(imageCalculator.setImage(speedVal), speedVal);
                oneRow.setImage_ratingTime(imageCalculator.setImage(timeVal), timeVal);

                allRows.add(oneRow);
            }
        }
    }
}
