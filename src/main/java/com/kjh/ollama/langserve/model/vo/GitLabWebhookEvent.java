package com.kjh.ollama.langserve.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.gitlab4j.api.models.Project;

public record GitLabWebhookEvent(
        @JsonProperty(value = "event_type")
        String eventType,
        GitLabProjectInfo project,
        @JsonProperty(value = "object_attributes")
        ObjectAttributes objectAttributes) {
}
