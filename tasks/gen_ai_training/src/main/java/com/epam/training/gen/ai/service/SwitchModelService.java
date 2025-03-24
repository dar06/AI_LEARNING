package com.epam.training.gen.ai.service;

import com.epam.training.gen.ai.service.factory.ChatCompletionServiceFactory;
import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SwitchModelService {
    private final ChatCompletionServiceFactory chatCompletionServiceFactory;

    public SwitchModelService(ChatCompletionServiceFactory chatCompletionServiceFactory) {
        this.chatCompletionServiceFactory = chatCompletionServiceFactory;
    }

    public String chatResponse(String input, String modelId, double temperature) {
        MODELNAME modelname = MODELNAME.valueOf(modelId);
        ChatCompletionService chatCompletionService = chatCompletionServiceFactory.createChatCompletionService(modelname.getCode());
        List<ChatMessageContent<?>> messageContents = chatCompletionService.getChatMessageContentsAsync(input, null, chatCompletionServiceFactory.createInvocationContext(temperature)).block();

        return messageContents.stream()
                .filter(Objects::nonNull)
                .filter(msg -> msg.getAuthorRole() == AuthorRole.ASSISTANT && msg.getContent() != null)
                .map(msg -> msg.getContent())
                .collect(Collectors.joining(System.lineSeparator()));

    }

}
