package com.example.demo.Services;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Users;
import com.example.demo.Payload.Response.ConversationResponse;

public interface IConversationService {
    void createConversation(Users users);

    ConversationResponse getCurrentConversation(String phoneNumber) throws DataNotFoundException;
}
