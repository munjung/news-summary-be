package com.news.summary.common.exception;

import com.news.summary.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.fail(e.getErrorCode().getCode(), e.getErrorCode().getMessage()));
    }
}