package com.userservice.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

}


//public class UserNotFoundException extends ApiException {
//    public UserNotFoundException(String message) {
//        super(message);
//    }
//
//    @Override
//    public int getStatusCode() {
//        return 404;
//    }
//}
