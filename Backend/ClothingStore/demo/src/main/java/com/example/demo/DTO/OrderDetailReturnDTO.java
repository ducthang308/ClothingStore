package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailReturnDTO {

    @JsonProperty("number_of_product")
    private Integer numberOfProduct;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("image_url")
    private String imageUrl;
}
