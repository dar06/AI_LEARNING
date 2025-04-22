package com.epam.training.gen.ai.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceUtils {

    public static JsonNode toJasonPayload(String content) throws JsonProcessingException {
        Map<String, Object> payload = new HashMap<>();
        payload.put("text", content);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(payload);
    }


    @NotNull
    public static float[] toFloatArray(List<Float> embeddingFromText) {
        float[] vectorComponents = new float[embeddingFromText.size()];
        for (int i = 0; i < embeddingFromText.size(); i++) {
            vectorComponents[i] = embeddingFromText.get(i);
        }
        return vectorComponents;
    }
}
