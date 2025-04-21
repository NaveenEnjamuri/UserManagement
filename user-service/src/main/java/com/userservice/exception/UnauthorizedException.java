package com.usermanagement.exception;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
