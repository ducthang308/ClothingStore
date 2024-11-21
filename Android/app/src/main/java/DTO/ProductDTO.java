package DTO;

import com.google.gson.annotations.SerializedName;

public class ProductDTO {
    private int id;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("category_id")
    private int categoryId;
    private String color;
    private float price;

    public ProductDTO(int id, String productName, int categoryId, String color, float price) {
        this.id = id;
        this.productName = productName;
        this.categoryId = categoryId;
        this.color = color;
        this.price = price;
    }

    public ProductDTO() {
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

    public int getCategory() {
        return categoryId;
    }

    public void setCategory(int categoryId) {
        this.categoryId = categoryId;
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
