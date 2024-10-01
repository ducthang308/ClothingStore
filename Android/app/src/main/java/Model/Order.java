package Model;

import java.sql.Date;

public class Order {
    private int id;
    private int userId;
    private String address;
    private String note;
    private Date orderDate;
    private String shippingMethod;
    private Date shippingDate;
    private String status;

    public Order() {
    }

    // Constructor
    public Order(int id, int userId, String address, String note, Date orderDate, String shippingMethod, Date shippingDate, String status) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.note = note;
        this.orderDate = orderDate;
        this.shippingMethod = shippingMethod;
        this.shippingDate = shippingDate;
        this.status = status;
    }

    // Getters and Setters
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
