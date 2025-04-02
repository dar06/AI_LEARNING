package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.ChatResponse;
import com.epam.training.gen.ai.service.BasicChatService;
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

    private final BasicChatService basicChatService;


    @Autowired
    public ChatServiceController(BasicChatService basicChatService) {
        this.basicChatService = basicChatService;

    }

    @GetMapping
    public ResponseEntity<Mono<List<ChatResponse>>> getChatbotResponse(@RequestParam String prompt) {
        return ResponseEntity.ok(basicChatService.getChatResponse(prompt));
    }
}

