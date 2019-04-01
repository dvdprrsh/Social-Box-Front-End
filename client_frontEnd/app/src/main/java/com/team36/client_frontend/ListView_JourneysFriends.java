package com.team36.client_frontend;
// David Parrish - 201232252

public class ListView_JourneysFriends {
    private int image_ratingImage;
    private String text_nameDay;
    private double rating_ratingStars;
    private String text_ratingDouble;

    private int image_ratingAcceleration;
    public Double rating_acceleration;

    private int image_ratingBraking;
    public Double rating_braking;

    private int image_ratingSpeed;
    public Double rating_speed;

    private int image_ratingTime;
    public Double rating_time;


    // Lines 24 to 88 are all methods for setting and getting the values for each component in a row/journey
    public void setImage_ratingImage(int image_ratingImage) {
        this.image_ratingImage = image_ratingImage;
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

    public void setImage_ratingAcceleration(int image_ratingAcceleration, Double rating_acceleration){
        this.image_ratingAcceleration = image_ratingAcceleration;
        this.rating_acceleration = rating_acceleration;
    }

    public int getImage_ratingAcceleration(){
        return image_ratingAcceleration;
    }

    public void setImage_ratingBraking(int image_ratingBraking, Double rating_braking){
        this.image_ratingBraking = image_ratingBraking;
        this.rating_braking = rating_braking;
    }

    public int getImage_ratingBraking(){
        return image_ratingBraking;
    }

    public void setImage_ratingSpeed(int image_ratingSpeed, Double rating_speed){
        this.image_ratingSpeed = image_ratingSpeed;
        this.rating_speed = rating_speed;
    }

    public int getImage_ratingSpeed(){
        return image_ratingSpeed;
    }

    public void setImage_ratingTime(int image_ratingTime, Double rating_time){
        this.image_ratingTime = image_ratingTime;
        this.rating_time = rating_time;
    }

    public int getImage_ratingTime(){
        return image_ratingTime;
    }

}
