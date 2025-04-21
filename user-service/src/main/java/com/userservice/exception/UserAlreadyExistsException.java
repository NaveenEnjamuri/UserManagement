package com.usermanagement.exception;

public class UserAlreadyExistsException extends ApiException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 409; // Conflict
    }
}
