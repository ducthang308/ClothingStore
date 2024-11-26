package com.example.demo.DTO;

import lombok.*;

import java.util.Date;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String content;
    private Long receiverId;
    private Date createAt;
    private String senderFullName;
}
