package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/ai/chat")
public class ChatServiceController {

    private final ChatService chatService;


    @Autowired
    public ChatServiceController(ChatService chatService) {
        this.chatService = chatService;

    }

    @GetMapping
    public ResponseEntity<Mono<List<String>>> getChatbotResponse(@RequestParam String prompt) {
        return ResponseEntity.ok(chatService.getChatResponse(prompt));
    }
}
