package com.example.demo.Services;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Conversation;
import com.example.demo.Models.Messages;
import com.example.demo.Models.Users;
import com.example.demo.Payload.Request.CreateMessageRequest;
import com.example.demo.Repository.ConversationRepository;
import com.example.demo.Repository.MessagesRepository;
import com.example.demo.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService{
    private final MessagesRepository messagesRepository;

    private final ConversationRepository conversationRepository;
    private final UsersRepository usersRepository;

    @Override
    public Messages createMessage(CreateMessageRequest messageRequest) throws DataNotFoundException {
        Date current = new Date();
        Messages message = new Messages();
        Users sender = usersRepository.findById(messageRequest.getSenderId())
                .orElseThrow(() -> new DataNotFoundException("Sender not found"));

        Users receiver = usersRepository.findById(messageRequest.getReceiverId())
                .orElseThrow(() -> new DataNotFoundException("Receiver not found"));

        message.setSenderId(sender);
        message.setReceiverId(receiver);
        message.setContent(messageRequest.getContent());
        message.setCreateAt(current);

        Optional<Conversation> conOptional= conversationRepository.findById(messageRequest.getConversationId());
        if(conOptional.isEmpty())
            System.out.println("==> Invalid Conversation");
        message.setConversation(conOptional.get());
        messagesRepository.save(message);
        return message;
    }

    @Override
    public List<Messages> getAllMessageByConversationId(int conversationId) {
        return null;
    }
}
