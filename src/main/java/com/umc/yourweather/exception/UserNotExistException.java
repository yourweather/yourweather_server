package com.umc.yourweather.exception;

public class UserNotExistException extends RuntimeException {

    String message;

    public UserNotExistException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
