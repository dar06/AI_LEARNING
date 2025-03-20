package com.epam.training.gen.ai.plugin;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

public class GreetingPlugin {

    @DefineKernelFunction(name = "greet", description = "greet by name")
    public String greet(@KernelFunctionParameter(
            name = "name",
            description = "Please provide name",
            type = String.class, required = true) String name) {
        return "Nice to meet you " + name;
    }

}
