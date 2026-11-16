package com.ch4project.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        if (requestUri.startsWith("/actuator")) {
            if (e instanceof NoResourceFoundException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
            }
        }

        log.error("[API - ERROR] 예외 발생 스택트레이스:", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러가 발생했습니다.");
    }
}