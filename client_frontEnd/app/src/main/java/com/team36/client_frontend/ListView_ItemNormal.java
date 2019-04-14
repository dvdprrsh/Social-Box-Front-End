package com.team36.client_frontend;
// David Parrish - 201232252

public class ListView_ItemNormal {
    // Variables to store values of each component of each row/friend
    private int image_ratingImage;
    private String text_nameDay;
    private double rating_ratingStars;
    private String text_ratingDouble;

    // These methods below (lines 12 to 44) are for setting and getting details of each row/friend
    void setImage_ratingImage(int image_pp) {
        this.image_ratingImage = image_pp;
    }

    int getImage_ratingImage() {
        return image_ratingImage;
    }

    void setText_nameDay(String text_name) {
        this.text_nameDay = text_name;
    }

    String getText_nameDay() {
        return text_nameDay;
    }

    void setRating_ratingStars(double rating_stars) {
        this.rating_ratingStars = rating_stars;
    }

    float getRating_ratingStars() {
        return (float) rating_ratingStars;
    }

    void setRating_ratingString(double rating_double) {
        this.text_ratingDouble = Double.toString(rating_double);
    }

    String getRating_ratingString() {
        return text_ratingDouble;
    }
}
