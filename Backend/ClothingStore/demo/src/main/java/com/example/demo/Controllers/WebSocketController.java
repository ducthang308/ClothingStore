package com.example.demo.Controllers;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Messages;
import com.example.demo.Payload.Request.CreateMessageRequest;
import com.example.demo.Services.ConversationService;
import com.example.demo.Services.MessageService;
import com.example.demo.Services.UserService;
import com.example.demo.Services.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final MessageService messageService;
    private final ConversationService conversationService;
    private final WebSocketService webSocketService;
    private final UserService userService;

    @MessageMapping("/message_send")
    public void sendToSpecificUser(@Payload CreateMessageRequest message) throws DataNotFoundException {
        System.out.println("====> receive message: " + message.toString());
        Messages mess = messageService.createMessage(message);
        webSocketService.sendMessagePrivate(mess);
    }
}
