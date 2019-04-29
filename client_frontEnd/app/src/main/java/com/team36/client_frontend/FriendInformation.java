package com.team36.client_frontend;

public class FriendInformation {
    public String friendUsername;
    public double[] friendScores;

    public FriendInformation(String friendUsername, int[] friendScores) {
        this.friendUsername =  friendUsername;
        this.friendScores = new StarCalculator(friendScores).starRatings;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public double[] getFriendScores() {
        return friendScores;
    }
}
