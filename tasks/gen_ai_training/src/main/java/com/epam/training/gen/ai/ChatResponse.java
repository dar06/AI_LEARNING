package com.epam.training.gen.ai;

import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;

public class ChatResponse {
    AuthorRole authorRole;
    String message;

    public ChatResponse(AuthorRole authorRole, String message) {
        this.authorRole = authorRole;
        this.message = message;
    }

    public AuthorRole getAuthorRole() {
        return authorRole;
    }

    public void setAuthorRole(AuthorRole authorRole) {
        this.authorRole = authorRole;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
