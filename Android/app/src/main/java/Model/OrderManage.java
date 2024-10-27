package Model;

import java.util.Date;

public class OrderManage {
    private int id;
    private Date dateCreate ;
    private float price;
    private float totalMoney;
    private String fullname;
    private String productName;
    private int numberOfProduct;
    private String status;
    private int imageUrl;

    public OrderManage(int id, Date dateCreate, float price, float totalMoney, String fullname, String productName, int numberOfProduct, String status, int imageUrl) {
        this.id = id;
        this.dateCreate = dateCreate;
        this.price = price;
        this.totalMoney = totalMoney;
        this.fullname = fullname;
        this.productName = productName;
        this.numberOfProduct = numberOfProduct;
        this.status = status;
        this.imageUrl = imageUrl;
    }

    public OrderManage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
