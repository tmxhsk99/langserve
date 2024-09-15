package com.kjh.ollama.langserve.service;


import com.kjh.ollama.langserve.model.vo.ReviewComment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OllamaCodeReviewServiceImpl implements AICodeReviewService{
    // todo 추후 구현 필요
    @Override
    public List<ReviewComment> reviewCode(String filePath, String fileContent) {
        return null;
    }
}
