package com.userservice.exception;

public class InvalidTokenException extends ApiException {

    public InvalidTokenException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
