package DTO;

import com.google.gson.annotations.SerializedName;

public class ReviewDTO {
    @SerializedName("product_id")
    private int productId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("note")
    private String note;

    @SerializedName("number_of_stars")
    private float numberOfStars;

    public ReviewDTO(int productId, int userId, int orderId, String note, float numberOfStars) {
        this.productId = productId;
        this.userId = userId;
        this.orderId = orderId;
        this.note = note;
        this.numberOfStars = numberOfStars;
    }

    public ReviewDTO() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(float numberOfStars) {
        this.numberOfStars = numberOfStars;
    }
}
