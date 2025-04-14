package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.service.EmbeddingService;
import com.fasterxml.jackson.core.JacksonException;
import io.metaloom.qdrant.client.http.impl.HttpErrorException;
import io.metaloom.qdrant.client.http.model.point.ScoredPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class EmbeddingServiceController {
    private final EmbeddingService embeddingService;

    public EmbeddingServiceController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping("/embeddings")
    public ResponseEntity<List<Float>> getEmbeddingFromText(@RequestParam String text) {
        return ResponseEntity.ok(embeddingService.getEmbeddingFromText(text));
    }

    @GetMapping(value="/save")
    public ResponseEntity<String> saveEmbeddingFromText(@RequestParam String text) throws JacksonException, HttpErrorException {
        return ResponseEntity.ok(embeddingService.saveEmbeddingFromText(text));
    }

    @GetMapping(value="/search")
    public ResponseEntity<ScoredPoint> searchEmbeddingFromText(@RequestParam String text) throws JacksonException, HttpErrorException {
        return ResponseEntity.ok(embeddingService.searchEmbeddingFromText(text));
    }


}
