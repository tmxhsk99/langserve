package com.kjh.ollama.langserve.model.vo;

import org.gitlab4j.api.models.Commit;

public record ObjectAttributes(Long iid, Commit lastCommit, String sourceBranch) {
}
