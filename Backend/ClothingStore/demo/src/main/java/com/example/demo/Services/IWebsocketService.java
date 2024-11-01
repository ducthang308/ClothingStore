package com.example.demo.Services;

import com.example.demo.Models.Messages;

public interface IWebsocketService {
    void sendMessagePrivate(Messages messages);
}
