package com.example.demo.DTO;

import com.example.demo.Models.OrderDetail;
import com.example.demo.Models.Orders;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductImages;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailReturnDTO {
    @JsonProperty("order_id")
    private Orders orders;

    @JsonProperty("product_id")
    private Product product;

    @JsonProperty("number_of_product")
    private Integer numberOfProduct;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("product_images_id")
    private ProductImages productImages;
}
