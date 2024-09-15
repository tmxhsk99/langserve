package com.kjh.ollama.langserve.model.vo;

import org.gitlab4j.api.models.Project;

public record GitLabWebhookEvent(String eventType, Project project, ObjectAttributes objectAttributes) {
}
