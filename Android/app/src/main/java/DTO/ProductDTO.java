package DTO;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProductDTO {
    @SerializedName("id")
    private int id;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("category_id")
    private int categoryId;

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
