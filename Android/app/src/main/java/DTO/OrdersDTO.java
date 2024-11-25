package DTO;

import java.util.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import Model.Discount;
import Model.User;

public class OrdersDTO {
<<<<<<< HEAD
    private Long id;

    @SerializedName("user_id")
    private Long userId;
=======
    @SerializedName("id")
    private int id;

    @SerializedName("users")
    private User user;
>>>>>>> origin/main

    @SerializedName("note")
    private String note;

    @SerializedName("order_date")
    private Date orderDate;

    @SerializedName("status")
    private String status;

    public OrdersDTO(Long id, Date orderDate, Float totalMoney, Long userId, String status) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalMoney = totalMoney;
        this.userId = userId;
        this.status = status;
    }

    @SerializedName("total_money")
    private Float totalMoney;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("orderDetails")
    private List<OrderDetailDTO> orderDetails;

<<<<<<< HEAD
    @SerializedName("discount_id")
    private Long discountId;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
=======
    @SerializedName("discounts")
    private Discount discounts;

    public OrdersDTO(int id, User user, String note, Date orderDate, String status, Float totalMoney, String paymentMethod, List<OrderDetailDTO> orderDetails, Discount discounts) {
        this.id = id;
        this.user = user;
        this.note = note;
        this.orderDate = orderDate;
        this.status = status;
        this.totalMoney = totalMoney;
        this.paymentMethod = paymentMethod;
        this.orderDetails = orderDetails;
        this.discounts = discounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
>>>>>>> origin/main
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

<<<<<<< HEAD
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

=======
    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Discount getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Discount discounts) {
        this.discounts = discounts;
    }
>>>>>>> origin/main
}

