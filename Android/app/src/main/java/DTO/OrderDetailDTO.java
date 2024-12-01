package DTO;

import com.google.gson.annotations.SerializedName;

public class OrderDetailDTO {
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

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(int orderId, int productId, Float price, int numberOfProduct, Float totalMoney) {
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.numberOfProduct = numberOfProduct;
        this.totalMoney = totalMoney;

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

}