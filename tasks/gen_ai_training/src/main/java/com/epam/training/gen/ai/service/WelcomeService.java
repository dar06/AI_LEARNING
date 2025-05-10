package com.epam.training.gen.ai.service;

import com.epam.training.gen.ai.ChatResponse;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.services.ServiceNotFoundException;
import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WelcomeService {

    private final Kernel kernel;

    @Autowired
    public WelcomeService(Kernel kernel) {
        this.kernel = kernel;
    }

    public List<ChatResponse> welcome(String prompt) throws ServiceNotFoundException {
        ChatCompletionService chatCompletionService = kernel.getService(ChatCompletionService.class);
        //function invoke
        InvocationContext invocationContext = InvocationContext.builder()
                .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true)).build();
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.addMessage(new ChatMessageContent(AuthorRole.USER, prompt));
        List<ChatMessageContent<?>> responseList = chatCompletionService.getChatMessageContentsAsync(
                chatHistory,
                kernel,
                invocationContext).block();
        String response = responseList.stream()
                .filter(Objects::nonNull)
                .filter(msg -> msg.getAuthorRole() == AuthorRole.ASSISTANT && msg.getContent() != null)
                .map(msg -> msg.getContent())
                .collect(Collectors.joining(System.lineSeparator()));
        chatHistory.addMessage(new ChatMessageContent(AuthorRole.ASSISTANT, response));

        return chatHistory.getMessages().stream().map(msg -> new ChatResponse(msg.getAuthorRole(), msg.getContent())).collect(Collectors.toList());
    }
}
