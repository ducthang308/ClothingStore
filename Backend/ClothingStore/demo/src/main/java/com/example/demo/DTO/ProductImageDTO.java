package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("image_url")
    private String imageUrl;
}
