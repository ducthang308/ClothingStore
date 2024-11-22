package Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String productName;
    private int categoryId;
    private String color;
    private String size;
    private float price;
    private float discount;

    public Product(String productName, String size, float price) {
        this.id = id;
        this.productName = productName;
        this.categoryId = categoryId;
        this.size = size;
        this.color = color;
        this.price = price;
        this.discount = discount;
    }

    public Product(int i, String áoThunNam, int i1, int i2, String m, String number, String s) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}