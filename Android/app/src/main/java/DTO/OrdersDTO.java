package DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class OrdersDTO {
    @SerializedName("user_id")
    private int userId;

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
    private int discountId;

    public OrdersDTO(int userId, String note, Date orderDate, String status, Float totalMoney, String paymentMethod, List<CartItemDTO> cartItems, int discountId) {
        this.userId = userId;
        this.note = note;
        this.orderDate = orderDate;
        this.status = status;
        this.totalMoney = totalMoney;
        this.paymentMethod = paymentMethod;
        this.cartItems = cartItems;
        this.discountId = discountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }
}
