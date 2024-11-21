package DTO;

import java.util.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrdersDTO {
    @SerializedName("user_id")
    private Long userId;

    @SerializedName("note")
    private String note;

    @SerializedName("order_date")
    private Date orderDate;

    @SerializedName("status")
    private String status;

    @SerializedName("total_money")
    private Float totalMoney;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("cart_items")
    private List<CartItemDTO> cartItems;

    @SerializedName("discount_id")
    private Long discountId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }
}
