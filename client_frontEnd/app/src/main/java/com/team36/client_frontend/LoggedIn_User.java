package com.team36.client_frontend;
// David Parrish - 201232252

public class LoggedIn_User {
    // Stores information about the user
    String api_key;
    String user_firstName;
    String user_lastName;
    String user_email;
    String user_username;
    double[] user_ratings;
    double user_overall = calcOverall();
    String[] user_friendIDs;
    String api ;




    // Assigns the variables above
    public LoggedIn_User(String key, String firstName, String lastName, String email,
                                    String username, int[] rating, String[] friendIDs){
        user_firstName = firstName;
        user_lastName = lastName;
        user_email = email;
        user_username = username;
        user_ratings = new StarCalculator(rating).starRatings;
        user_friendIDs = friendIDs;
        api = key;
    }

    // Calculates the overall rating for user logged in
    private double calcOverall(){
        if (user_ratings != null) {
            OverallCalculator overallCalculator = new OverallCalculator(user_ratings);
            return overallCalculator.overallRating;
        }
        return 0;
    }
}
