package com.epam.training.gen.ai.service;

import com.epam.training.gen.ai.ChatResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChatService {
    Mono<List<ChatResponse>>  getChatResponse(String prompt);
}
