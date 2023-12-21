package com.umc.yourweather.exception;

public class UserAlreadyExistException extends RuntimeException {

    String message;

    public UserAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}