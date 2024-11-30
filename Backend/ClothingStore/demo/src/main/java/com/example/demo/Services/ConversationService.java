package com.example.demo.Services;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Conversation;
import com.example.demo.Models.Messages;
import com.example.demo.Models.Users;
import com.example.demo.Payload.Request.CreateConversationRequest;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationService implements IConversationService {

    private final ConversationRepository conversationRepository;

    private final UsersRepository usersRepository;

    @Override
    public void createConversation(CreateConversationRequest request) throws DataNotFoundException {
        // Truy xuất người gửi và người nhận từ cơ sở dữ liệu
        Users sender = usersRepository.findById((long) request.getSenderId())
                .orElseThrow(() -> new DataNotFoundException("Sender not found"));

        Users receiver = usersRepository.findById((long) request.getReceiverId())
                .orElseThrow(() -> new DataNotFoundException("Receiver not found"));

        // Tạo Conversation mới
        Conversation conversation = new Conversation();
        conversation.setSenderId(sender);
        conversation.setReceiverId(receiver);
        conversation.setCreateAt(new Date());

        // Lưu vào cơ sở dữ liệu
        conversationRepository.save(conversation);
    }
    @Override
    public ConversationResponse getCurrentConversation(String phoneNumber, Long receiverId) throws DataNotFoundException {
        // Tìm người dùng từ số điện thoại
        Users user = usersRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Tìm receiver
        Users receiver = usersRepository.findById(receiverId)
                .orElseThrow(() -> new DataNotFoundException("Receiver not found"));

        // Tìm cuộc hội thoại giữa user và receiver
        Optional<Conversation> optionalConversation = conversationRepository.findBySenderAndReceiver(user, receiver);
        Conversation conversation = optionalConversation
                .orElseThrow(() -> new DataNotFoundException("No conversation found between the user and receiver"));

        // Tạo response
        ConversationResponse resp = new ConversationResponse();
        List<MessageResponse> messageResponses = new ArrayList<>();

        // Duyệt qua các tin nhắn trong cuộc hội thoại
        for (Messages message : conversation.getMessages()) {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setId(message.getId());
            messageResponse.setSenderId(message.getSenderId().getId().intValue());
            messageResponse.setReceiverId(message.getReceiverId().getId().intValue());
            messageResponse.setContent(message.getContent());
            messageResponse.setCreateAt(message.getCreateAt());

            messageResponses.add(messageResponse);
        }

        resp.setId(conversation.getId());
        resp.setMessageResponses(messageResponses);

        return resp;
    }
}
