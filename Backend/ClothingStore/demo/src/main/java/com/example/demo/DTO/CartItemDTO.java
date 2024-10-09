package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("quantity")
    private Integer quantity;
}
