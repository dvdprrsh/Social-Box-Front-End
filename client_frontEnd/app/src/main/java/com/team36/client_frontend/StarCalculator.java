package com.team36.client_frontend;

public class StarCalculator {
    double[] starRatings;

    StarCalculator(double[] ratings){
        starRatings = new double[ratings.length];

        for (int i = 0; i < starRatings.length; i++) {
            starRatings[i] = getStar(ratings[i]);
        }
    }

    private double getStar(double rating){

        if (rating >= 80.0){
            return 5.0;
        }else if (rating >= 75.0 && rating < 80.0){
            return 4.5;
        }else if (rating >= 70.0 && rating < 75.0){
            return 4.0;
        }else if (rating >= 65.0 && rating < 70.0){
            return 3.5;
        }else if (rating >= 60.0 && rating < 65.0){
            return 3.0;
        }else if (rating >= 55.0 && rating < 60.0){
            return 2.5;
        }else if (rating >= 50.0 && rating < 55.0){
            return 2.0;
        }else if (rating >= 45.0 && rating < 50.0){
            return 1.5;
        }else if (rating >= 40.0 && rating < 45.0){
            return 1.0;
        }else if (rating >= 35.0 && rating < 40.0){
            return 0.5;
        }else {
            return 0.0;
        }

    }
}
