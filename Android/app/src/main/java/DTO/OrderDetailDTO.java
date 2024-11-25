package DTO;

import com.google.gson.annotations.SerializedName;

public class OrderDetailDTO {
    private int id;

    @SerializedName("order_id")
    private int orderId;
    @SerializedName("product_id")
    private int productId;
    @SerializedName("price")
    private Float price;
    @SerializedName("number_of_product")
    private int numberOfProduct;
    @SerializedName("total_money")
    private Float totalMoney;
    @SerializedName("color")
    private String color;

<<<<<<< HEAD
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getOrderId() {
=======
    public OrderDetailDTO(int orderId, int productId, Float price, int numberOfProduct, Float totalMoney, String color) {
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.numberOfProduct = numberOfProduct;
        this.totalMoney = totalMoney;
        this.color = color;
    }

    public int getOrderId() {
>>>>>>> main
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {

    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
