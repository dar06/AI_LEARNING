package com.epam.training.gen.ai.service;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.services.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverterService {

    private final Kernel kernel;

    @Autowired
    public CurrencyConverterService(Kernel kernel) {
        this.kernel = kernel;
    }

    public double getRate(String ccy1, String ccy2) throws ServiceNotFoundException {

       var rate = kernel.getPlugin("CurrencyConverterPlugin")
                .get("exchangeRate")
                .invokeAsync(kernel)
                .withArguments(KernelFunctionArguments.builder()
                        .withVariable("currency1", ccy1)
                        .withVariable("currency2", ccy2).build())
                .withResultType(Double.class)
                .block();
        return rate.getResult();
    }

    public double convertAmount(String ccy1, String ccy2, double amount) throws ServiceNotFoundException {

        var rate = kernel.getPlugin("CurrencyConverterPlugin")
                .get("convertAmount")
                .invokeAsync(kernel)
                .withArguments(KernelFunctionArguments.builder()
                        .withVariable("currency1", ccy1)
                        .withVariable("currency2", ccy2)
                        .withVariable("amount", amount)
                        .build())
                .withResultType(Double.class)
                .block();
        return rate.getResult();
    }
}
