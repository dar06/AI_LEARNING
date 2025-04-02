package com.epam.training.gen.ai.service;

import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//Demonstrates PromptExecutionSettings with the specified settings

@Service
public class PromptSettingService {
    private final ChatCompletionService chatCompletionService;

    public PromptSettingService(ChatCompletionService chatCompletionService) {
        this.chatCompletionService = chatCompletionService;
    }

    public String displayMessage(String input, double temperature) {
        List<ChatMessageContent<?>> messageContents = chatCompletionService.getChatMessageContentsAsync(input, null, createInvocationContext(temperature)).block();

        String response = messageContents.stream()
                .filter(Objects::nonNull)
                .filter(msg -> msg.getAuthorRole() == AuthorRole.ASSISTANT && msg.getContent() != null)
                .map(msg -> msg.getContent())
                .collect(Collectors.joining(System.lineSeparator()));
        return response;

    }

    private InvocationContext createInvocationContext(double temperature) {
        return new InvocationContext.Builder()
                .withPromptExecutionSettings(PromptExecutionSettings.builder().withTemperature(temperature).build())
                .build();
    }
}
