package DTO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductDTO implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("category_id")
    private int categoryId;

    private int quantity;

    public ProductDTO(int id, String productName, int categoryId, int quantity, String color, Float price, List<String> imageUrls) {
        this.id = id;
        this.productName = productName;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.color = color;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    public ProductDTO(int id, String productName, Float price, List<String> imageUrls) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    @SerializedName("color")
    private String color;

    @SerializedName("price")
    private Float price;

    @SerializedName("image_urls")
    private List<String> imageUrls; // Danh sách URL hình ảnh

    public ProductDTO(int id, String productName, int categoryId, String color, Float price, List<String> imageUrls) {
        this.id = id;
        this.productName = productName;
        this.categoryId = categoryId;
        this.color = color;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    public ProductDTO(String productName, int categoryId, String color, Float price, List<String> imageUrls) {
        this.productName = productName;
        this.categoryId = categoryId;
        this.color = color;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    public ProductDTO(int id, String productName, String color, Float price, List<String> imageUrls) {
        this.productName = productName;
        this.categoryId = categoryId;
        this.color = color;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    public ProductDTO(String productName, Float price, List<String> imageUrls) {
        this.productName = productName;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    public ProductDTO(String productName, Float price, String color, List<String> imageUrls) {
        this.color = color;
        this.productName = productName;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    public ProductDTO() {

    }

    public ProductDTO(String s, int i, String s1, int ao) {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        if (imageUrls != null) {
            this.imageUrls = imageUrls;
        }
    }
}
