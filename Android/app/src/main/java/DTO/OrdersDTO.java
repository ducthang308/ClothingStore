package DTO;

import java.util.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrdersDTO {
    @SerializedName("user_id")
    private Long userId;

    @SerializedName("note")
    private String note;

    @SerializedName("order_date")
    private Date orderDate;

    @SerializedName("status")
    private String status;

    @SerializedName("total_money")
    private Float totalMoney;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("cart_items")
    private List<CartItemDTO> cartItems;

    @SerializedName("discount_id")
    private Long discountId;
}
