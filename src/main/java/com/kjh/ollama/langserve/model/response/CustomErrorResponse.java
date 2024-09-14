package com.kjh.ollama.langserve.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public record CustomErrorResponse(int code, String message, Map<String, String> validation) {
    @Builder
    public CustomErrorResponse(int code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String field, String defaultMessage) {
        validation.put(field, defaultMessage);
    }
}
