package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountsDTO {
    @JsonProperty("percent")
    private float percent;

    @JsonProperty("user_id")
    private Long userId;
}
