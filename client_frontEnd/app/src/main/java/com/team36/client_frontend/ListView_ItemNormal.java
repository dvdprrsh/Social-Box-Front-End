package com.team36.client_frontend;

public class ListView_ItemNormal {
    // Variables to store values of each component of each row/friend
    private int image_ratingImage;
    private String text_nameDay;
    private double rating_ratingStars;
    private String text_ratingDouble;

    // These methods below (lines 10 to 32) are for setting and getting details of each row/friend
    public void setImage_ratingImage(int image_pp) {
        this.image_ratingImage = image_pp;
    }

    public int getImage_ratingImage() {
        return image_ratingImage;
    }

    public void setText_nameDay(String text_name) {
        this.text_nameDay = text_name;
    }

    public String getText_nameDay() {
        return text_nameDay;
    }

    public void setRating_ratingStars(double rating_stars) {
        this.rating_ratingStars = rating_stars;
    }

    public float getRating_ratingStars() {
        return (float) rating_ratingStars;
    }

    public void setRating_ratingString(double rating_double) {
        this.text_ratingDouble = Double.toString(rating_double);
    }

    public String getRating_ratingString() {
        return text_ratingDouble;
    }
}
