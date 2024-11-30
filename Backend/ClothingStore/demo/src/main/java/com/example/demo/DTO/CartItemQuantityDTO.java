package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemQuantityDTO {
    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
