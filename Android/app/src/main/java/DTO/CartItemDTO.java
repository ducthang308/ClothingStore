package DTO;

import com.google.gson.annotations.SerializedName;

public class CartItemDTO {


    @SerializedName("productId")
    private int productId;

    @SerializedName("quantity")
<<<<<<< HEAD
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
=======
    private int quantity;

    public CartItemDTO(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
>>>>>>> main
        this.quantity = quantity;
    }
}
