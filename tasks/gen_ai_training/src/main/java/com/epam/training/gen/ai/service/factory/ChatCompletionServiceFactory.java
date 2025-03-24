package com.epam.training.gen.ai.service.factory;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.epam.training.gen.ai.service.MODELNAME;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import org.springframework.stereotype.Component;

@Component
public class ChatCompletionServiceFactory {
    private final OpenAIAsyncClient openAIAsyncClient;

    public ChatCompletionServiceFactory(OpenAIAsyncClient openAIAsyncClient) {
        this.openAIAsyncClient = openAIAsyncClient;
    }

    public ChatCompletionService createChatCompletionService(String modelId) {

        return OpenAIChatCompletion.builder()
                .withModelId(modelId)
                .withOpenAIAsyncClient(openAIAsyncClient)
                .build();
    }

    public InvocationContext createInvocationContext(double temperature) {
        return new InvocationContext.Builder()
                .withPromptExecutionSettings(PromptExecutionSettings.builder().withTemperature(temperature).build())
                .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
                .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
                .build();
    }
}
