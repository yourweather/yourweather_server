package com.umc.yourweather.exception;

public class WeatherNotFoundException extends RuntimeException {

    String message;

    public WeatherNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
