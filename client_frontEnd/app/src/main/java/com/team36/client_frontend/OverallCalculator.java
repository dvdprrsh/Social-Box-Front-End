package com.team36.client_frontend;
// David Parrish - 201232252

class OverallCalculator {
    double[] starRatings;
    double overallRating;

    OverallCalculator(double[] ratings){
        starRatings = new StarCalculator(ratings).starRatings;
        overallRating = calcOverall(starRatings);
    }

    // Calculates the overall (average) rating from the ratings provided
    private double calcOverall(double[] ratings){
        double sum = 0.0;
        for (double r : ratings){ // Loops through each element in ratings
            sum += r;
        }

        double average = sum/ratings.length;
        average = (Math.round(average*2))/2.0; /* This is used to have the average rounded to
                                                *  0.5 (so 4.4 will be 4.5 but 4.2 will be 4.0) */
        return average;
    }

}
