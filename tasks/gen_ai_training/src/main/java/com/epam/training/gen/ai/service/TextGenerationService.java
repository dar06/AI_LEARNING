package com.epam.training.gen.ai.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import io.metaloom.qdrant.client.http.impl.HttpErrorException;
import io.metaloom.qdrant.client.http.model.point.PointStruct;
import io.metaloom.qdrant.client.json.JsonException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.epam.training.gen.ai.service.EmbeddingService.createPointStruct;

@Service
public class TextGenerationService {
    private final EmbeddingService embeddingService;
    private final ChatCompletionService chatCompletionService;

    private final InvocationContext invocationContext;

    public TextGenerationService(EmbeddingService embeddingService, ChatCompletionService chatCompletionService, InvocationContext invocationContext) {
        this.embeddingService = embeddingService;
        this.chatCompletionService = chatCompletionService;
        this.invocationContext = invocationContext;
    }

    public String generateEmbeddingsAndSave(List<String> contents) throws HttpErrorException, JacksonException, JsonException {
        List<PointStruct> pointStructs = new ArrayList<>();
        int[] index = {1};
        contents.stream().forEach(content -> {
            List<Float> embeddingFromText = embeddingService.getEmbeddingFromText(content);
            try {
                PointStruct pointStruct = createPointStruct(index, content, embeddingFromText, "text_embeddings6");
                pointStructs.add(pointStruct);
                index[0]++;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (JacksonException e) {
                throw new RuntimeException(e);
            }


        });
        return embeddingService.saveEmbeddings("fileCollections6", "text_embeddings6", pointStructs);
    }

    public String search(String prompt) throws JsonException, HttpErrorException {
        String context = embeddingService.searchEmbeddingFromText(prompt, "fileCollections6", "text_embeddings6");
        ChatHistory chatHistory = new ChatHistory();
        String input = String.format("Context:\n%s\n\nQuestion:\n%s", context, prompt);
        chatHistory.addMessage(new ChatMessageContent(AuthorRole.SYSTEM, "You are a helpful assistant. Use the context below to answer the user's question."));
        chatHistory.addMessage(new ChatMessageContent(AuthorRole.USER, String.format("Context:\n%s\n\nQuestion:\n%s", context, input)));
        List<ChatMessageContent<?>> results = chatCompletionService
                .getChatMessageContentsAsync(chatHistory, null, invocationContext).block();
        String response = results.stream()
                .filter(Objects::nonNull)
                .filter(msg -> msg.getAuthorRole() == AuthorRole.ASSISTANT && msg.getContent() != null)
                .map(msg -> msg.getContent())
                .collect(Collectors.joining(System.lineSeparator()));

        return response;
    }




}
