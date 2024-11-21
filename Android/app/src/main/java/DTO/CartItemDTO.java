package DTO;
import com.google.gson.annotations.SerializedName;
public class CartItemDTO {
    @SerializedName("productId")
    private Long productId;

    @SerializedName("quantity")
    private Integer quantity;
}
