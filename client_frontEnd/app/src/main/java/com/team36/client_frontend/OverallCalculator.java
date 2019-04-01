package com.team36.client_frontend;
// David Parrish - 201232252

public class OverallCalculator {
    public double overallRating;

    OverallCalculator(double[] ratings){
        overallRating = calcOverall(ratings);
    }

    // Calculates the overall (average) rating from the ratings provided
    private double calcOverall(double[] ratings){
        double sum = 0.0;
        for (int i=0; i<ratings.length; i++){
            sum += ratings[i];
        }

        double average = sum/ratings.length;
        average = (Math.round(average*2))/2.0; /* This is used to have the average rounded to
                                               *  0.5 (so 4.4 will be 4.5 but 4.2 will be 4.0) */
        return average;
    }

}
