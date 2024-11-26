package com.example.demo.Services;

import com.example.demo.DTO.MessageDTO;
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

import java.util.*;

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

        // Tìm hoặc tạo cuộc hội thoại giữa sender và receiver, không quan tâm thứ tự
        Conversation conversation = conversationRepository.findBySenderAndReceiver(sender, receiver)
                .orElseGet(() -> {
                    // Nếu không tìm thấy, tạo mới Conversation
                    Conversation newConversation = new Conversation();
                    newConversation.setSenderId(sender);
                    newConversation.setReceiverId(receiver);
                    newConversation.setCreateAt(current);
                    conversationRepository.save(newConversation);
                    return newConversation;
                });

        // Tạo message mới
        message.setSenderId(sender);
        message.setReceiverId(receiver);
        message.setContent(messageRequest.getContent());
        message.setCreateAt(current);
        message.setConversation(conversation);

        messagesRepository.save(message);
        return message;
    }

    @Override
    public List<Messages> getAllMessageByConversationId(Long conversationId) {
        return messagesRepository.findByConversationId(conversationId);
    }

//    @Override
//    public List<Messages> getAllSenderId(Long receiverId) {
//        return messagesRepository.getAllSenderId(receiverId);
//    }
//
//    @Override
//    public List<Messages> getAllMessagesByReceiverId(Long receiverId) {
//        return messagesRepository.findByReceiverId(receiverId);
//    }

    public List<MessageDTO> getLatestMessagesBySender(List<MessageDTO> allMessages) {
        Map<String, MessageDTO> latestMessagesBySender = new HashMap<>();
        for (MessageDTO message : allMessages) {
            String senderName = message.getSenderFullName();
            //Kiểm tra xem Map đã chứa tên người gửi này hay chưa
            if (!latestMessagesBySender.containsKey(senderName)) {
                //Nếu chưa thì thêm vào Map
                latestMessagesBySender.put(senderName, message);
            } else {
                //Lấy tin nhắn hiện tại của người gửi từ Map
                MessageDTO existingMessage = latestMessagesBySender.get(senderName);
                //Nếu tin nhắn mới (message) có thời gian createAt lớn hơn tin nhắn đang lưu trong Map
                if (message.getCreateAt().after(existingMessage.getCreateAt())) {
                    //Cập nhật lại tin nhắn mới nhất cho người gửi bằng cách đặt tin nhắn mới (message) vào Map
                    latestMessagesBySender.put(senderName, message);
                }
            }
        }
        return new ArrayList<>(latestMessagesBySender.values());
    }

    @Override
    public List<MessageDTO> getAllMessagesByReceiverId(Long receiverId) {
        List<MessageDTO> allMessages = messagesRepository.getAllSenderId(receiverId);
        List<MessageDTO> result = getLatestMessagesBySender(allMessages);
        return result;
    }
}
