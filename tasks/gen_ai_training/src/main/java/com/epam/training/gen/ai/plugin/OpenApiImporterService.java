package com.epam.training.gen.ai.plugin;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.implementation.EmbeddedResourceLoader;
import com.microsoft.semantickernel.orchestration.FunctionResult;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionYaml;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenApiImporterService {
    private final ChatCompletionService chatCompletionService;

    @Autowired
    public OpenApiImporterService(ChatCompletionService chatCompletionService, Kernel kernel) {
        this.chatCompletionService = chatCompletionService;
    }

    public String generateAutobiography(String person, Integer lines) throws IOException {

        String yaml = EmbeddedResourceLoader.readFile("GenerateAutobiography.yaml", OpenApiImporterService.class);
        KernelFunction<String> yamlfunction = KernelFunctionYaml.fromPromptYaml(yaml);
        Kernel kernel = Kernel.builder()
                .withAIService(ChatCompletionService.class, chatCompletionService).build();

        FunctionResult<String> result = yamlfunction
                .invokeAsync(kernel)
                .withArguments(
                        KernelFunctionArguments.builder()
                                .withVariable("person", person)
                                .withVariable("length", lines)
                                .build())
                .block();

        return result.getResult();


    }
}

