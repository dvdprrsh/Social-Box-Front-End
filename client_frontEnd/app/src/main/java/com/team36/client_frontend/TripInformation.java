package com.team36.client_frontend;

public class TripInformation {
    private double[] scores;
    private String slang_time;
    private String trip_id;

    public TripInformation(int[] scores, String slang_time, String trip_id) {
        this.scores = new StarCalculator(scores).starRatings;
        this.slang_time = slang_time;
        this.trip_id = trip_id;
    }

    public double[] getScores() {
        return scores;
    }

    public String getSlang_time() {
        return slang_time;
    }
}
