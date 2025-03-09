package com.epam.training.gen.ai.service;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ChatService {

    private final ChatCompletionService chatCompletionService;

    private final Kernel kernel;

    private final InvocationContext invocationContext;

    @Autowired
    public ChatService(ChatCompletionService chatCompletionService, Kernel kernel, InvocationContext invocationContext) {
        this.chatCompletionService = chatCompletionService;
        this.kernel = kernel;
        this.invocationContext = invocationContext;
    }

    public Mono<List<String>> getChatResponse(String prompt) {
        return chatCompletionService
                .getChatMessageContentsAsync(prompt, kernel, invocationContext)
                .map(results -> results.stream()
                        .filter(Objects::nonNull)
                        .filter(msg -> msg.getAuthorRole() == AuthorRole.ASSISTANT && msg.getContent() != null)
                        .map(ChatMessageContent::getContent)
                        .collect(Collectors.toList()));
    }
}



