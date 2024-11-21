package DTO;
import com.google.gson.annotations.SerializedName;

public class OrderDetailDTO {
    @SerializedName("order_id")
    private Long orderId;

    @SerializedName("product_id")
    private Long productId;

    @SerializedName("price")
    private Float price;

    @SerializedName("number_of_product")
    private int numberOfProduct;

    @SerializedName("total_money")
    private Float totalMoney;

    @SerializedName("color")
    private String color;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
