package com.team36.client_frontend;

public class listview_itemDashboard {
    // Variables to store values of each component of each row/friend
    private String text_dashboardSection;
    private double rating_dashboard;

    // These methods below (lines 10 to 22) are for setting and getting details of each row/journey
    public void setText_section(String text_dashboardSection) {
        this.text_dashboardSection = text_dashboardSection;
    }

    public String getText_section() {
        return text_dashboardSection;
    }

    public void setRating_stars(double rating_journey) {
        this.rating_dashboard = rating_journey;
    }

    public double getRating_stars() {
        return rating_dashboard;
    }

    public void setText_rating(Double rating_journey) {
        this.rating_dashboard = rating_journey;
    }

    public String getText_rating() {
        return Double.toString(rating_dashboard);
    }
}
