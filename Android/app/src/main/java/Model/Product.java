package Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String productName;
    private Category category;
    private String color;
    private float price;

    public Product(int id, String productName, Category category, String color, float price) {
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.color = color;
        this.price = price;
    }

    public Product() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
