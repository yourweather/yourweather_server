package com.umc.yourweather.exception;

public class MemoNotFoundException extends RuntimeException {

    String message;

    public MemoNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
