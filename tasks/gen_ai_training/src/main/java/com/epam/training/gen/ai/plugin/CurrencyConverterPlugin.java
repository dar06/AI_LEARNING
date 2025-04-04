package com.epam.training.gen.ai.plugin;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

public class CurrencyConverterPlugin {

    @DefineKernelFunction(name = "convertAmount", description = "convert currency from one to other")
    public static double convertAmount(
            @KernelFunctionParameter(name = "currency1", description = "Please provide first currency", type = String.class, required = true) String currency1,
            @KernelFunctionParameter(name = "currency2", description = "Please provide second currency", type = String.class, required = true) String currency2,
            @KernelFunctionParameter(name = "amount", description = "Please provide amount", type = double.class, required = true) double amount) {
        return getFxRates(currency1, currency2) * amount;
    }

    @DefineKernelFunction(name = "exchangeRate", description = "exchange rate of given currencies")
    public static double exchangeRate(
            @KernelFunctionParameter(name = "currency1", description = "Please provide first currency", type = String.class, required = true) String currency1,
            @KernelFunctionParameter(name = "currency2", description = "Please provide second currency", type = String.class, required = true) String currency2) {
        return getFxRates(currency1, currency2);
    }

    private static double getFxRates(String currency1, String currency2) {
        if (currency1.equals(currency2)) {
            return 1.0;
        } else if (currency1.equals("USD") && currency2.equals("EUR")) {
            return 0.92;
        } else if (currency1.equals("EUR") && currency2.equals("USD")) {
            return 1.08;
        } else {
            throw new IllegalArgumentException("Currency not supported");
        }
    }




}
