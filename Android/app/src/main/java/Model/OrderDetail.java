package Model;

import java.sql.Date;

public class OrderDetail {
    private int id;
    private int orderId;
    private int productId;
    private float price;
    private int numberOfProduct;
    private float totalMoney;
    private Date orderDate;

    public OrderDetail() {
    }

    // Constructor
    public OrderDetail(int id, int orderId, int productId, float price, int numberOfProduct, float totalMoney, Date orderDate) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.numberOfProduct = numberOfProduct;
        this.totalMoney = totalMoney;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}

