package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.ChatResponse;
import com.epam.training.gen.ai.service.BasicChatService;
import com.epam.training.gen.ai.service.FunctionService;
import com.epam.training.gen.ai.service.HistoryChatService;
import com.epam.training.gen.ai.service.PromptSettingService;
import com.microsoft.semantickernel.services.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ai/chat")
public class ChatServiceController {
    private final List<ChatResponse> chatHistoryList = new ArrayList<>();

    private final BasicChatService basicChatService;

    private final PromptSettingService promptSettingService;

    private final FunctionService functionService;

    private final HistoryChatService historyChatService;


    @Autowired
    public ChatServiceController(BasicChatService basicChatService, PromptSettingService promptSettingService, FunctionService functionService, HistoryChatService historyChatService) {
        this.basicChatService = basicChatService;
        this.promptSettingService = promptSettingService;
        this.historyChatService = historyChatService;
        this.functionService = functionService;
    }

    @GetMapping("/basic")
    public ResponseEntity<Mono<List<ChatResponse>>> getChatbotResponse(@RequestParam String prompt) {
        return ResponseEntity.ok(basicChatService.getChatResponse(prompt));
    }

    @GetMapping("/prompt")
    public ResponseEntity<String> getChatResponse(@RequestParam String input, @RequestParam double temperature) {
        return ResponseEntity.ok(promptSettingService.displayMessage(input, temperature));
    }

    @GetMapping("/function")
    public ResponseEntity<List<ChatResponse>> getFunction(@RequestParam String prompt) throws
            ServiceNotFoundException {
        List<ChatResponse> chatResponse = functionService.getChatResponse(prompt);
        chatHistoryList.addAll(chatResponse);
        chatHistoryList.forEach(res -> System.out.println(res));
        return ResponseEntity.ok(chatHistoryList);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatResponse>> getChatCompletionWithHistory(@RequestParam String prompt) {
        List<ChatResponse> chatResponse = historyChatService.getChatResponseFromChatCompletionWithHistory(prompt);
        chatHistoryList.addAll(chatResponse);
        chatHistoryList.forEach(res -> System.out.println(res));
        return ResponseEntity.ok(chatHistoryList);
    }


}

