package com.team36.client_frontend;

public class ImageCalculator {
    public int image;

    ImageCalculator(double rating){
        image = setImage(rating);
    }

    // Sets the image for each card view dependant on the rating of each section rating
    public int setImage(Double sectionRating){
        switch (Double.toString(sectionRating)){
            case "1.0":
            case "1.5":
                return (R.drawable.ic_sentiment_very_dissatisfied_black_48dp);

            case "2.0":
            case "2.5":
                return (R.drawable.ic_sentiment_dissatisfied_black_48dp);

            case "3.0":
                return (R.drawable.ic_sentiment_neutral_black_48dp);

            case "3.5":
                return (R.drawable.ic_sentiment_neutral_black_48dp);

            case "4.0":
                return (R.drawable.ic_sentiment_satisfied_black_48dp);

            case "4.5":
                return (R.drawable.ic_sentiment_very_satisfied_black_48dp);

            case "5.0":
                return (R.drawable.ic_whatshot_black_48dp);
        }
        return 0;
    }

}
