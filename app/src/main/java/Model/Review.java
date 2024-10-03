package Model;

public class Review {
    private String reviewerName;
    private String reviewContent;
    private float rating;
    private int imageuser;

    public Review(String reviewerName, String reviewContent, float rating, int imageResource) {
        this.reviewerName = reviewerName;
        this.reviewContent = reviewContent;
        this.rating = rating;
        this.imageuser = imageuser;
    }


    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }



    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public int getImageuser() {
        return imageuser;
    }

    public void setImageuser(int imageuser) {
        this.imageuser = imageuser;
    }
}
