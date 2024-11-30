package DTO;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import Model.Discount;
import Model.User;

public class OrderReturnDTO {
    @SerializedName("id")
    private int id;

    @SerializedName("users")
    private User user;

    @SerializedName("note")
    private String note;

    @SerializedName("order_date")
    private Date orderDate;

    @SerializedName("status")
    private String status;

    @SerializedName("total_money")
    private Float totalMoney;

    public OrderReturnDTO(int id, User user, String note, Date orderDate, String status, Float totalMoney) {
        this.id = id;
        this.user = user;
        this.note = note;
        this.orderDate = orderDate;
        this.status = status;
        this.totalMoney = totalMoney;
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
}
