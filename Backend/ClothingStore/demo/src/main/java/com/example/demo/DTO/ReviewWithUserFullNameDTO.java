package com.example.demo.DTO;

import com.example.demo.Models.Review;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewWithUserFullNameDTO {
    private Review review;
    private String fullName;
}
