package com.example.demo.Services;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Messages;
import com.example.demo.Payload.Request.CreateMessageRequest;

import java.util.List;

public interface IMessageService {
    Messages createMessage(CreateMessageRequest messageRequest) throws DataNotFoundException;

    List<Messages> getAllMessageByConversationId(int conversationId);
}
