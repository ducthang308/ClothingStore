package DTO;
import com.google.gson.annotations.SerializedName;
public class CartItemDTO {


    @SerializedName("productId")
    private Long productId;

    @SerializedName("quantity")
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
        this.quantity = quantity;
    }
}
