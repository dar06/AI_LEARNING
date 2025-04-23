package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.ChatResponse;
import com.epam.training.gen.ai.plugin.OpenApiImporterService;
import com.epam.training.gen.ai.service.*;
import com.microsoft.semantickernel.services.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
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

    private final SwitchModelService switchModelService;

    private final CompareModelService compareModelService;

    private final CurrencyConverterService currencyConverterService;

    private final WelcomeService welcomeService;

    private final OpenApiImporterService openApiImporterService;


    @Autowired
    public ChatServiceController(BasicChatService basicChatService, PromptSettingService promptSettingService, FunctionService functionService, HistoryChatService historyChatService,
                                 SwitchModelService switchModelService, CompareModelService compareModelService,
                                 CurrencyConverterService currencyConverterService, WelcomeService welcomeService, OpenApiImporterService openApiImporterService) {
        this.basicChatService = basicChatService;
        this.promptSettingService = promptSettingService;
        this.historyChatService = historyChatService;
        this.functionService = functionService;
        this.switchModelService = switchModelService;
        this.compareModelService = compareModelService;
        this.currencyConverterService = currencyConverterService;
        this.welcomeService = welcomeService;
        this.openApiImporterService = openApiImporterService;

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

    @GetMapping("/switch")
    public ResponseEntity<String> getChatCompletion(@RequestParam String input, String modelId, double temperature) {
        String response = switchModelService.chatResponse(input, modelId, temperature);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/compare")
    public ResponseEntity<String> compare() {
        String response = compareModelService.compare();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rate")
    public ResponseEntity<Double> currencyConverter(@RequestParam String currency1, @RequestParam String currency2) throws ServiceNotFoundException {
        return ResponseEntity.ok(currencyConverterService.getRate(currency1, currency2));
    }

    @GetMapping("/amount")
    public ResponseEntity<Double> amountConverter(@RequestParam String currency1, @RequestParam String currency2, @RequestParam double amount) throws ServiceNotFoundException {
        return ResponseEntity.ok(currencyConverterService.convertAmount(currency1, currency2, amount));
    }

    @GetMapping("/welcome")
    public ResponseEntity<List<ChatResponse>> welcome(@RequestParam String name) throws
            ServiceNotFoundException {
        List<ChatResponse> chatResponse = welcomeService.welcome(name);
        chatHistoryList.addAll(chatResponse);
        return ResponseEntity.ok(chatHistoryList);
    }

    @GetMapping("/autobio")
    public ResponseEntity<String> generateAutobiography(@RequestParam String person, @RequestParam Integer lines) throws ServiceNotFoundException, IOException {
        return ResponseEntity.ok(openApiImporterService.generateAutobiography(person, lines));
    }


}

