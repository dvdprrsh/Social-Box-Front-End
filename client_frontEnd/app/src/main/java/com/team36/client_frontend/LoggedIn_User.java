package com.team36.client_frontend;

public class LoggedIn_User {
    public String user_id;
    public String user_firstName;
    public String user_lastName;
    public String user_email;
    public String user_mobileNo;
    public String user_username;
    public Double user_rating;
    public String[] user_friendIDs;

    LoggedIn_User(String id, String firstName, String lastName, String email,
                  String mobileNo, String username, double rating, String[] friendIDs){
        user_id = id;
        user_firstName = firstName;
        user_lastName = lastName;
        user_email = email;
        user_mobileNo = mobileNo;
        user_username = username;
        user_rating = rating;
        user_friendIDs = friendIDs;
    }
}
