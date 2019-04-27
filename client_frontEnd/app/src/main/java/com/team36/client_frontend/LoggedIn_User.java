package com.team36.client_frontend;
// David Parrish - 201232252

public class LoggedIn_User {
    // Stores information about the user
    String api_key;
    String user_firstName = "David";
    String user_lastName;
    String user_email;
    String user_username;
    double[] user_ratings = {4.0, 4.0, 5.0, 3.5};
    double user_overall = calcOverall();
    String[] user_friendIDs;
    String api = "566fdade-11ae-4405-a1a2-f333b3cd4da7";


    // Assigns the variables above
    void LoggedIn_UserMethod(String key, String firstName, String lastName, String email,
                                    String username, double[] rating, String[] friendIDs, String Givenapi){
        api_key = key;
        user_firstName = firstName;
        user_lastName = lastName;
        user_email = email;
        user_username = username;
        user_ratings = rating;
        user_friendIDs = friendIDs;
        api = Givenapi;
    }

    // Calculates the overall rating for user logged in
    private double calcOverall(){
        OverallCalculator overallCalculator = new OverallCalculator(user_ratings);
        return overallCalculator.overallRating;
    }
}
