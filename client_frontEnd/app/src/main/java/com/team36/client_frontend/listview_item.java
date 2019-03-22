package com.team36.client_frontend;

public class listview_item {
    // Variables to store values of each component of each row/friend
    private int image_friendPP;
    private String text_friendName;
    private double rating_friendRating;
    private String rating_doubleRating;

    // These methods below (lines 10 to 32) are for setting and getting details of each row/friend
    public void setImage_pp(int image_pp) {
        this.image_friendPP = image_pp;
    }

    public int getImage_pp() {
        return image_friendPP;
    }

    public void setText_name(String text_name) {
        this.text_friendName = text_name;
    }

    public String getText_name() {
        return text_friendName;
    }

    public void setRating_stars(double rating_stars) {
        this.rating_friendRating = rating_stars;
    }

    public double getRating_stars() {
        return rating_friendRating;
    }

    public void setRating_double(double rating_double) {
        this.rating_doubleRating = Double.toString(rating_double);
    }

    public String getRating_double() {
        return rating_doubleRating;
    }
}
