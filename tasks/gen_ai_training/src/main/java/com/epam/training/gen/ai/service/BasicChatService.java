package com.epam.training.gen.ai.service;

import com.epam.training.gen.ai.ChatResponse;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class BasicChatService implements ChatService{

    private final ChatCompletionService chatCompletionService;

    private final Kernel kernel;

    private final InvocationContext invocationContext;

    @Autowired
    public BasicChatService(ChatCompletionService chatCompletionService, Kernel kernel, InvocationContext invocationContext) {
        this.chatCompletionService = chatCompletionService;
        this.kernel = kernel;
        this.invocationContext = invocationContext;
    }

    public Mono<List<ChatResponse>> getChatResponse(String prompt){

        return chatCompletionService
                .getChatMessageContentsAsync(prompt, kernel, invocationContext)
                .map(results -> results.stream()
                        .filter(Objects::nonNull)
                        .map(msg -> new ChatResponse(msg.getAuthorRole(), msg.getContent()))
                        .collect(Collectors.toList()));
    }
}



