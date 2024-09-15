package com.kjh.ollama.langserve.controller;

import com.kjh.ollama.langserve.exception.common.BaseException;
import com.kjh.ollama.langserve.model.response.CustomErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.kjh.ollama.langserve.util.MessageUtil.*;

/**
 * 전체 컨트롤 공통 에러처리 ControllerAdvice
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    /**
     * 요청이 잘못된 경우 에러 처리
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CustomErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        log.error(toErrorMessageFormat(e));
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "잘못된 요청 입니다.", e.getFieldErrors());
    }

    /**
     * 사용자 정의 커스텀 에러 처리
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> baseExceptionHandler(BaseException e) {
        log.error(toErrorMessageFormat(e));
        CustomErrorResponse errorResponse = buildErrorResponse(e);
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    /**
     * 그 이외의 예외 처리
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CustomErrorResponse globalExceptionHandler(Exception e) {
        log.error(toErrorMessageFormat(e));
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, toErrorMessageFormat(e));
    }

    private CustomErrorResponse buildErrorResponse(HttpStatus status, String message, List<FieldError> fieldErrors) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .code(status.value())
                .message(message)
                .build();
        for (FieldError fieldError : fieldErrors) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    private CustomErrorResponse buildErrorResponse(BaseException e) {
        return CustomErrorResponse.builder()
                .code(e.getStatusCode())
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();
    }

    private CustomErrorResponse buildErrorResponse(HttpStatus status, String message) {
        return CustomErrorResponse.builder()
                .code(status.value())
                .message(message)
                .build();
    }

}
