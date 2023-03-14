package com.task.dropit.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String invalidRequest) {
        super(invalidRequest);
    }
}
