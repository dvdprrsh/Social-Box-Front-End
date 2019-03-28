package com.team36.client_frontend;

public class OverallCalculator {
    public double overallRating;

    OverallCalculator(double[] ratings){
        overallRating = calcOverall(ratings);
    }
    private double calcOverall(double[] ratings){
        double sum = 0.0;
        for (int i=0; i<ratings.length; i++){
            sum += ratings[i];
        }

        double average = sum/ratings.length;
        average = (Math.round(average*2))/2.0;

        return average;
    }

}
