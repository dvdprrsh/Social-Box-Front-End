package com.team36.client_frontend;

public class listview_itemJourneys {
    // Variables to store values of each component of each row/friend
    private String text_journeyDate;
    private double rating_journey;

    // These methods below (lines 10 to 22) are for setting and getting details of each rows/friend
    public void setText_journeyDate(String text_journeyDate) {
        this.text_journeyDate = text_journeyDate;
    }

    public String getText_journeyDate() {
        return text_journeyDate;
    }

    public void setRating_stars(double rating_journey) {
        this.rating_journey = rating_journey;
    }

    public double getRating_stars() {
        return rating_journey;
    }
}
