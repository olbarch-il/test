package com.task.dropit.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError("Internal Server Error");
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private long timestamp;
}
