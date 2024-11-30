package com.example.demo.Services;

import com.example.demo.DTO.MessageDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Messages;
import com.example.demo.Payload.Request.CreateMessageRequest;

import java.util.List;

public interface IMessageService {
    Messages createMessage(CreateMessageRequest messageRequest) throws DataNotFoundException;

    List<Messages> getAllMessageByConversationId(Long conversationId);

//    List<Messages> getAllSenderId(Long receiverId);

    List<MessageDTO> getAllMessagesByReceiverId(Long receiverId);
}
