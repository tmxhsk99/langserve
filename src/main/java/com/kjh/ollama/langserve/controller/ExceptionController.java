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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CustomErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        log.error(toErrorMessageFormat(e));
        CustomErrorResponse response = CustomErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("잘못된 요청 입니다.")
                .build();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> baseExceptionHandler(BaseException e) {
        log.error(toErrorMessageFormat(e));
        int statusCode = e.getStatusCode();

        CustomErrorResponse body = CustomErrorResponse.builder()
                .code(statusCode)
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<CustomErrorResponse> response = ResponseEntity.status(statusCode).body(body);

        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CustomErrorResponse globalExceptionHandler (Exception e){
        log.error(toErrorMessageFormat(e));
        return CustomErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(toErrorMessageFormat(e))
                .build();
    }



}
