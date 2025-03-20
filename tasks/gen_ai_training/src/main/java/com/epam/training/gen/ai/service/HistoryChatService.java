package com.epam.training.gen.ai.service;

import com.epam.training.gen.ai.ChatResponse;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//ChatCompletionService and ChatHistory Initialization
@Service
public class HistoryChatService {
    private final ChatCompletionService chatCompletionService;

    private final InvocationContext invocationContext;

    @Autowired
    public HistoryChatService(ChatCompletionService chatCompletionService, InvocationContext invocationContext) {
        this.chatCompletionService = chatCompletionService;
        this.invocationContext = invocationContext;
    }

    public List<ChatResponse> getChatResponseFromChatCompletionWithHistory(String prompt) {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.addMessage(new ChatMessageContent(AuthorRole.USER, prompt));
        List<ChatMessageContent<?>> results = chatCompletionService
                .getChatMessageContentsAsync(prompt, null, invocationContext).block(); //invocation context with temperature set
        String response = results.stream()
                .filter(Objects::nonNull)
                .filter(msg -> msg.getAuthorRole() == AuthorRole.ASSISTANT && msg.getContent() != null)
                .map(msg -> msg.getContent())
                .collect(Collectors.joining(System.lineSeparator()));
        chatHistory.addMessage(new ChatMessageContent(AuthorRole.ASSISTANT, response));
        return chatHistory.getMessages().stream().map(msg -> new ChatResponse(msg.getAuthorRole(), msg.getContent())).collect(Collectors.toList());
    }
}
