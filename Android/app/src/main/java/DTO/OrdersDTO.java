package DTO;

import java.util.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import Model.Discount;

public class OrdersDTO {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("note")
    private String note;

    @SerializedName("order_date")
    private String orderDate;

    @SerializedName("status")
    private String status;

    @SerializedName("total_money")
    private Float totalMoney;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("orderDetails")
    private List<OrderDetailDTO> orderDetails;


    @SerializedName("discount_id")
    private Integer discounts;

    public OrdersDTO() {
        // Khởi tạo các giá trị mặc định nếu cần
    }

    public OrdersDTO(int id, int userId, String note, String orderDate, String status, Float totalMoney, String paymentMethod, List<OrderDetailDTO> orderDetails, Integer discounts) {
        this.id = id;
        this.userId = userId;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
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

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Integer getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Integer discounts) {
        this.discounts = discounts;
    }
}