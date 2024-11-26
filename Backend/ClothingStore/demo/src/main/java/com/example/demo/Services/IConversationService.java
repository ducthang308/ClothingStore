package com.example.demo.Services;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Users;
import com.example.demo.Payload.Request.CreateConversationRequest;
import com.example.demo.Payload.Response.ConversationResponse;

public interface IConversationService {
    void createConversation(CreateConversationRequest request) throws DataNotFoundException;

    ConversationResponse getCurrentConversation(String phoneNumber, Long receiverId) throws DataNotFoundException;
}

