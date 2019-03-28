package com.team36.client_frontend;

public class listView_itemJourneys {
    private int image_ratingImage;
    private String text_nameDay;
    private double rating_ratingStars;
    private String text_ratingDouble;
    private int image_ratingAcceleration;
    private int image_ratingBraking;
    private int image_ratingSpeed;
    private int image_ratingTime;

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

    public void setImage_ratingAcceleration(int image_ratingAcceleration){
        this.image_ratingAcceleration = image_ratingAcceleration;
    }

    public int getImage_ratingAcceleration(){
        return image_ratingAcceleration;
    }

    public void setImage_ratingBraking(int image_ratingBraking){
        this.image_ratingBraking = image_ratingBraking;
    }

    public int getImage_ratingBraking(){
        return image_ratingBraking;
    }

    public void setImage_ratingSpeed(int image_ratingSpeed){
        this.image_ratingSpeed = image_ratingSpeed;
    }

    public int getImage_ratingSpeed(){
        return image_ratingSpeed;
    }

    public void setImage_ratingTime(int image_ratingTime){
        this.image_ratingTime = image_ratingTime;
    }

    public int getImage_ratingTime(){
        return image_ratingTime;
    }

}
