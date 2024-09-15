package com.kjh.ollama.langserve.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.gitlab4j.api.models.Commit;

public record ObjectAttributes(
        Long iid,
        @JsonProperty("last_commit")
        Commit lastCommit,
        @JsonProperty("source_branch")
        String sourceBranch) {
}
