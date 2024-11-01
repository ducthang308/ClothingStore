package com.example.demo.Services;

import com.example.demo.Models.Messages;
import com.example.demo.Payload.Response.MessagePacketResponse;
import com.example.demo.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
@RequiredArgsConstructor
public class WebSocketService implements IWebsocketService {

    private final SimpMessagingTemplate messagingTemplate;

    private UsersRepository usersRepository;

    @Override
    public void sendMessagePrivate(Messages messages) {
        MessagePacketResponse response = new MessagePacketResponse();
        response.setCreateAt(messages.getCreateAt().getTime());
        response.setSenderId(messages.getSenderId().getId());
        response.setReceiverId(messages.getReceiverId().getId());
        response.setContent(messages.getContent());
        response.setConversationId(messages.getConversation().getId());

        System.out.println("==> sendMessagePrivate : " + response.toString());
        messagingTemplate.convertAndSendToUser(String.valueOf(response.getSenderId()), "/message_receive", response);
        messagingTemplate.convertAndSendToUser(String.valueOf(response.getReceiverId()), "/message_receive", response);
    }
}
