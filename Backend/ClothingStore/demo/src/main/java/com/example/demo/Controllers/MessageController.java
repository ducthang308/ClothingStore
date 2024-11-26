package com.example.demo.Controllers;

import com.example.demo.DTO.MessageDTO;
import com.example.demo.DTO.MessageResponseDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Messages;
import com.example.demo.Payload.Request.CreateMessageRequest;
import com.example.demo.Services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<Messages> createMessage(@RequestBody CreateMessageRequest messageRequest) {
        log.info("Creating new message: {}", messageRequest);
        try {
            Messages createdMessage = messageService.createMessage(messageRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage); // 201 Created nếu thành công
        } catch (DataNotFoundException e) {
            log.error("Data not found for creating message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 nếu không tìm thấy dữ liệu liên quan
        } catch (Exception e) {
            log.error("Error creating message: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 nếu xảy ra lỗi khác
        }
    }

    @GetMapping("/{conversationId}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<List<MessageResponseDTO>> getAllByConversationId(@PathVariable Long conversationId) {
        List<MessageResponseDTO> messages = messageService.getAllMessageByConversationId(conversationId)
                .stream()
                .map(message -> new MessageResponseDTO(
                        message.getId(),
                        message.getSenderId().getId(), // Lấy ID từ đối tượng Users
                        message.getReceiverId().getId(),
                        message.getContent(),
                        message.getCreateAt()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/receiverId/{receiverId}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public List<MessageDTO> getLatestMessages(@PathVariable Long receiverId) {
        return messageService.getAllMessagesByReceiverId(receiverId);
    }
}
