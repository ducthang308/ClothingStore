package com.example.demo.Controllers;

import com.example.demo.Models.Messages;
import com.example.demo.Services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;


    @GetMapping("/{conservationId}")
    public List<Messages> GetAllByConservationId(@PathVariable int conservationId) {
        return messageService.getAllMessageByConversationId(conservationId);
    }
}
