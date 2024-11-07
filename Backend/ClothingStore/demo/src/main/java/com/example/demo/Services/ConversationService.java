package com.example.demo.Services;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Conversation;
import com.example.demo.Models.Messages;
import com.example.demo.Models.Users;
import com.example.demo.Payload.Response.ConversationResponse;
import com.example.demo.Payload.Response.MessageResponse;
import com.example.demo.Repository.ConversationRepository;
import com.example.demo.Repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService implements IConversationService {

    private final ConversationRepository conversationRepository;

    private final UsersRepository usersRepository;

    @Override
    public void createConversation(Users users) {
        Conversation conversation = new Conversation();
        conversation.setSenderId(users);
        conversation.setReceiverId(users);
        conversation.setCreateAt(new Date());
        conversationRepository.save(conversation);
    }

    @Override
    public ConversationResponse getCurrentConversation(String phoneNumber) throws DataNotFoundException {
        // Tìm người dùng bằng số điện thoại
        Users user = usersRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Tìm conversation liên quan đến người dùng này
        Conversation conversation = conversationRepository.findByUser(user)
                .orElseThrow(() -> new DataNotFoundException("No conversation found for this user"));

        // Chuẩn bị response cho conversation
        ConversationResponse resp = new ConversationResponse();
        List<MessageResponse> messageResponses = new ArrayList<>();

        for (Messages message : conversation.getMessages()) {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setId(message.getId());
            messageResponse.setSenderId(message.getSenderId().getId().intValue());
            messageResponse.setReceiverId(message.getReceiverId().getId().intValue());
            messageResponse.setContent(message.getContent());
            messageResponse.setCreateAt(message.getCreateAt());

            messageResponses.add(messageResponse);
        }

        // Set các giá trị vào response
        resp.setId(conversation.getId());
        resp.setMessageResponses(messageResponses);

        return resp;
    }

}
