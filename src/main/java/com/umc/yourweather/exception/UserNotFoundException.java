package com.umc.yourweather.exception;

public class UserNotFoundException extends RuntimeException {

    String message;

    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
