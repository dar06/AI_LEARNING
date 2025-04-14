package com.epam.training.gen.ai.service;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.EmbeddingItem;
import com.azure.ai.openai.models.Embeddings;
import com.azure.ai.openai.models.EmbeddingsOptions;
import com.fasterxml.jackson.core.JacksonException;
import io.metaloom.qdrant.client.http.QDrantHttpClient;
import io.metaloom.qdrant.client.http.impl.HttpErrorException;
import io.metaloom.qdrant.client.http.model.GenericBooleanStatusResponse;
import io.metaloom.qdrant.client.http.model.collection.CollectionCreateRequest;
import io.metaloom.qdrant.client.http.model.collection.CollectionListResponse;
import io.metaloom.qdrant.client.http.model.collection.config.Distance;
import io.metaloom.qdrant.client.http.model.point.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmbeddingService {

    private final OpenAIAsyncClient openAIAsyncClient;
    private final QDrantHttpClient qdrantHttpClient;

    public EmbeddingService(OpenAIAsyncClient openAIAsyncClient, QDrantHttpClient qdrantHttpClient) {
        this.openAIAsyncClient = openAIAsyncClient;
        this.qdrantHttpClient = qdrantHttpClient;
    }

    public List<Float> getEmbeddingFromText(String text) {
        EmbeddingsOptions embeddingsOptions = new EmbeddingsOptions(
                Arrays.asList(text));
        Embeddings embeddings = openAIAsyncClient.getEmbeddings("text-embedding-ada-002", embeddingsOptions).block();
        List<Float> embeddingValues = new ArrayList<>();
        for (EmbeddingItem item : embeddings.getData()) {
            for (Float embedding : item.getEmbedding()) {
                embeddingValues.add(embedding);
            }
        }
        return embeddingValues;

    }

    public String saveEmbeddingFromText(String text) throws HttpErrorException, JacksonException {
        List<Float> embeddingsForText = getEmbeddingFromText(text);

        CollectionListResponse listResponse = qdrantHttpClient.listCollections().async().blockingGet();
        boolean exists = listResponse.getResult().getCollections().stream()
                .anyMatch(c -> c.getName().equals("ai-training-collection"));

        if (!exists) {
            CollectionCreateRequest req = new CollectionCreateRequest();
            req.setVectors("text_embedding", 4, Distance.COSINE);
            GenericBooleanStatusResponse resposne = qdrantHttpClient.createCollection("ai-training-collection", req).sync();
        }
        PointStruct pointStruct = PointStruct.of("text_embedding", embeddingsForText.get(0), embeddingsForText.get(1), embeddingsForText.get(2), embeddingsForText.get(3))  //storing just 3 only from the list
                .setId(1);
        PointsListUpsertRequest pointsRequest = new PointsListUpsertRequest();
        pointsRequest.setPoints(pointStruct);
        UpdateResultResponse updateResultResponse = qdrantHttpClient.upsertPoints("ai-training-collection", pointsRequest, false).async().blockingGet();
        return updateResultResponse.getResult().getStatus().getName();
    }


    public ScoredPoint searchEmbeddingFromText(String text) throws HttpErrorException {
        List<Float> embeddingsForText = getEmbeddingFromText(text);
        PointsSearchRequest pointsSearchRequest = new PointsSearchRequest();
        NamedVector namedVector = new NamedVector().setName("text_embedding").setVector(embeddingsForText.subList(0, 4));
        pointsSearchRequest.setVector(namedVector);
        pointsSearchRequest.setLimit(3);
        PointsSearchResponse response = qdrantHttpClient.searchPoints("ai-training-collection", pointsSearchRequest).sync();
        return response.getResult().get(0);
    }


}
