package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.rag.FileChunker;
import com.epam.training.gen.ai.service.TextGenerationService;
import com.fasterxml.jackson.core.JacksonException;
import io.metaloom.qdrant.client.http.impl.HttpErrorException;
import io.metaloom.qdrant.client.json.JsonException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/rag")
public class RagController {
    private final TextGenerationService textGenerationService;
    private final FileChunker fileChunker;

    public RagController(TextGenerationService textGenerationService, FileChunker fileChunker) {
        this.textGenerationService = textGenerationService;
        this.fileChunker = fileChunker;
    }

    @GetMapping("/save")
    public ResponseEntity<String> generateTextFromGivenSourceAndSave(@RequestParam String fileName) throws FileNotFoundException, JacksonException, HttpErrorException, JsonException {

        return ResponseEntity.ok(textGenerationService.generateEmbeddingsAndSave(fileChunker.readFromFileAndCreateChunks(fileName, 500)));
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchText(@RequestParam String text) throws HttpErrorException, JsonException {
        return ResponseEntity.ok(textGenerationService.search(text));
    }

}
