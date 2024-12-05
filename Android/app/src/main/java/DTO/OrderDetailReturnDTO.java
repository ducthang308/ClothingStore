package DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class OrderDetailReturnDTO {
    @SerializedName("product_id")
    private int productId;

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("number_of_product")
    private int numberOfProduct;

    @SerializedName("total_money")
    private float totalMoney;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("address")
    private String address;

    @SerializedName("order_date")
    private Date orderDate;

    @SerializedName("status")
    private String status;

    public OrderDetailReturnDTO(int numberOfProduct, float totalMoney, String productName, String imageUrl, String address, Date orderDate, String status) {
        this.numberOfProduct = numberOfProduct;
        this.totalMoney = totalMoney;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.address = address;
        this.orderDate = orderDate;
        this.status = status;
    }

    public OrderDetailReturnDTO() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

