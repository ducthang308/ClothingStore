package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users users;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders orders;

    @Column(name = "image_url", length = 300)
    @JsonProperty("image_url")
    private String imageUrl;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "number_of_stars", length = 100)
    private float numberOfStars;
}
