package com.epam.training.gen.ai.plugin;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

public class WelcomePlugin {

    @DefineKernelFunction(name = "welcome", description = "welcome person by name")
    public String welcome(
            @KernelFunctionParameter(name = "name", description = "Please provide name to welcome", type = String.class, required = true) String name) {
        return "Welcome to world of AI " + name;
    }

}
