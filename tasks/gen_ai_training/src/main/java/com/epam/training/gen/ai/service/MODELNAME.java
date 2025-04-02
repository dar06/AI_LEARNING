package com.epam.training.gen.ai.service;

public enum MODELNAME {

    MISTRAL("rlab-mistral-instruct"),
    GOOGLE("chat-bison@001"),
    AMAZON("amazon.titan-tg1-large");
    private String code;

    MODELNAME(String code) {
        this.code = code;
    }
    public String getCode() {
            return code;
    }

}
