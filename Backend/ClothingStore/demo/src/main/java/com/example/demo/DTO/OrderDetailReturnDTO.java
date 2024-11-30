package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

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

    @JsonProperty("address")
    private String address;

    @JsonProperty("order_date")
    private Date orderDate;

    @Column(name = "status")
    private String status;
}
