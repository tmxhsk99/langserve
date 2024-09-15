package com.kjh.ollama.langserve.exception;

import com.kjh.ollama.langserve.exception.common.BaseException;

public class GitLabServiceException extends BaseException {
    public static String MESSAGE = "GitLab API 호출시 에러 발생";


    public GitLabServiceException() {
        super(MESSAGE);
    }

    public GitLabServiceException(String message) {
        super(message);
    }

    public GitLabServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 502;
    }
}
