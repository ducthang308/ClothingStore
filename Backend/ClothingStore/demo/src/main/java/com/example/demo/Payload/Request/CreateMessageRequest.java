package com.example.demo.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateMessageRequest {
//    protected Long senderId ;
//
//    protected Long receiverId;
//
//    protected String content;
//
//    protected long createAt;
//
//    protected Long conversationId ;

    protected Long senderId;
    protected Long receiverId;
    protected String content;
}
