package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;
}
