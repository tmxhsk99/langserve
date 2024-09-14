package com.kjh.ollama.langserve.service;

import com.kjh.ollama.langserve.model.vo.ReviewComment;

import java.util.List;


public interface AICodeReviewService {

    List<ReviewComment> reviewCode(String oldFileContent, String newFileContent);
}