package DTO;

import Model.Review;

public class ReviewWithUserFullNameDTO {
    private Review review;
    private String fullName;

    public ReviewWithUserFullNameDTO(Review review, String fullName) {
        this.review = review;
        this.fullName = fullName;
    }

    public ReviewWithUserFullNameDTO() {

    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
