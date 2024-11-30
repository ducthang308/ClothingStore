package com.example.demo.Controllers;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Conversation;
import com.example.demo.Payload.Request.CreateConversationRequest;
import com.example.demo.Services.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("${api.prefix}/conversation")
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;

    @GetMapping("/{receiverId}")
    public ResponseEntity<?> getConversation(@PathVariable Long receiverId) throws DataNotFoundException {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(conversationService.getCurrentConversation(phoneNumber, receiverId));
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> createConversation(@RequestBody CreateConversationRequest request) {
        try {
            conversationService.createConversation(request); // Gọi đến service
            return ResponseEntity.status(HttpStatus.CREATED).body("Conversation created successfully");
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating conversation");
        }
    }

}
