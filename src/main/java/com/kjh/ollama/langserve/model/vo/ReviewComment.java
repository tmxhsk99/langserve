package com.kjh.ollama.langserve.model.vo;

public record ReviewComment(String comment, // 리뷰 본문
                            Integer lineNumber,// 코멘트 할 라인이 단일 라인인 경우
                            Integer startLine, // 코멘트 할 라인이 범위인 경우 시작 라인
                            Integer endLine // 코멘트 할 라인이 범위인 경우 종료 라인
) {

}
