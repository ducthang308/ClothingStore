package DTO;

import com.google.gson.annotations.SerializedName;

public class CartItemDTO {
    @SerializedName("productId")
    private int productId;

    @SerializedName("quantity")
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
        this.quantity = quantity;
    }
}
