package com.team36.client_frontend;

public class LoggedIn_User {
    public String user_id;
    public String user_firstName = "David";
    public String user_lastName;
    public String user_email;
    public String user_mobileNo;
    public String user_username;
    public double[] user_ratings = {4.0, 4.0, 5.0, 3.5};
    public double user_overall = calcOverall();
    public String[] user_friendIDs;

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

    private double calcOverall(){
        double sum = 0.0;
        for (int i=0; i<user_ratings.length; i++){
            sum += user_ratings[i];
        }

        return sum/(user_ratings.length);
    }
}
