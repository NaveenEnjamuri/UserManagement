package com.userservice.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
//public class UserAlreadyExistsException extends ApiException {
//
//    public UserAlreadyExistsException(String message) {
//        super(message);
//    }
//
//    @Override
//    public int getStatusCode() {
//        return 409; // Conflict
//    }
//}
