package com.task.dropit.exception;

public class CapacityExceededException extends RuntimeException {
    private final String message;
    public CapacityExceededException(String message) {
        super(message);
        this.message=message;
    }
}
