package com.umc.yourweather.utils.messages;

public enum ErrorMessages {
    USER_NOT_EXIST("등록된 사용자가 존재하지 않습니다.");


    private final String message;
    private static final String prefix = "[ERROR] ";


    ErrorMessages(String message) {
        this.message = prefix + message;
    }

    public String getMessage() {
        return message;
    }
}


