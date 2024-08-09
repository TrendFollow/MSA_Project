package com.sparta.msa_exam.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private HttpStatus status;
    private String stackTrace;

    public ErrorResponse(LocalDateTime timestamp, String message, String stackTrace) {
        this.timestamp = timestamp;
        this.message = message;
        this.stackTrace = stackTrace;
    }
}
