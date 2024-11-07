package com.example.demo.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageResponse {

    protected Long id ;

    protected int senderId;

    protected int receiverId;

    protected String content;

    protected Date createAt;
}
