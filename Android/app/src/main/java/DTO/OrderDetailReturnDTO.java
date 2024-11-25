package DTO;

import com.google.gson.annotations.SerializedName;

public class OrderDetailReturnDTO {
    @SerializedName("number_of_product")
    private int numberOfProduct;

    @SerializedName("total_money")
    private Float totalMoney;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("image_url")
    private String imageUrl;

    public OrderDetailReturnDTO(int numberOfProduct, Float totalMoney, String productName, String imageUrl) {
        this.numberOfProduct = numberOfProduct;
        this.totalMoney = totalMoney;
        this.productName = productName;
        this.imageUrl = imageUrl;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
