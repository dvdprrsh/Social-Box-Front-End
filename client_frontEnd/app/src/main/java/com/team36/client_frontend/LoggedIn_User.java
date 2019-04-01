package com.team36.client_frontend;
// David Parrish - 201232252

public class LoggedIn_User {
    // Stores information about the user
    public String user_id;
    public String user_firstName = "David";
    public String user_lastName;
    public String user_email;
    public String user_mobileNo;
    public String user_username;
    public double[] user_ratings = {4.0, 4.0, 5.0, 3.5};
    public double user_overall = calcOverall();
    public String[] user_friendIDs;

    // Assigns the variables above
    public void LoggedIn_UserMethod(String id, String firstName, String lastName, String email,
                  String mobileNo, String username, double[] rating, String[] friendIDs){
        user_id = id;
        user_firstName = firstName;
        user_lastName = lastName;
        user_email = email;
        user_mobileNo = mobileNo;
        user_username = username;
        user_ratings = rating;
        user_friendIDs = friendIDs;
    }

    // Calculates the overall rating for user logged in
    private double calcOverall(){
        OverallCalculator overallCalculator = new OverallCalculator(user_ratings);
        return overallCalculator.overallRating;
    }
}
