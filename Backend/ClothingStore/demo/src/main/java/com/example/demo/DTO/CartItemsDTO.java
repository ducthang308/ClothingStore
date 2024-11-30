package com.example.demo.DTO;

import com.example.demo.Models.Carts;
import com.example.demo.Models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemsDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("cart_id")
    private Long cartId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("color")
    private String color;

    @JsonProperty("image_url")
    private String imageUrl;
}
