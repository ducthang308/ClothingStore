package com.example.demo.Controllers;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Services.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("${api.prefix}/conversation")
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;

    @GetMapping("")
    public ResponseEntity<?> getConversation() throws DataNotFoundException {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(conversationService.getCurrentConversation(phoneNumber));
    }
}
