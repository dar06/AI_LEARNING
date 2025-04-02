package com.epam.training.gen.ai.service;

import org.springframework.stereotype.Component;

@Component
public class CompareModelService {
    private final SwitchModelService switchModelService;

    public CompareModelService(SwitchModelService switchModelService) {
        this.switchModelService = switchModelService;
    }

    public String compare() {
        String input = "Describe alien planet not more than 10 words";
        String mistralResponse = switchModelService.chatResponse(input, "MISTRAL", 0.2);
        String amazonResponse = switchModelService.chatResponse(input, "AMAZON", 0.2);
        String googleResponse = switchModelService.chatResponse(input, "GOOGLE", 0.2);
        String mistralResponse1 = switchModelService.chatResponse(input, "MISTRAL", 1.0);
        String amazonResponse1 = switchModelService.chatResponse(input, "AMAZON", 1.0);
        String googleResponse1 = switchModelService.chatResponse(input, "GOOGLE", 1.0);

        return String.format("<b>Model id responses with temperature 0.1</b><br><b>Mistral</b>=[%s]<br><b>Amazon</b>=[%s]<br><b>Google</b>=[%s]<br><b>Model id responses with temperature 1.0</b><br><b>Mistral</b>=[%s]<br><b>Amazon</b>=[%s]<br><b>Google</b>=[%s]", mistralResponse, amazonResponse, googleResponse, mistralResponse1, amazonResponse1, googleResponse1);

    }
}
