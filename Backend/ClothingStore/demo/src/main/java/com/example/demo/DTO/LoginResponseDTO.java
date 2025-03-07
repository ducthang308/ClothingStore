package com.example.demo.DTO;

import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private Long roleId;
    private Long userId;
    private String name;
    private Long conversationId;
    private String address;
    private Long cartId;
    private String active;
}