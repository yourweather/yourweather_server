package com.umc.yourweather.utils.messages;

public enum ErrorMessages {
    USER_NOT_EXIST("등록된 사용자가 존재하지 않습니다."),
    USER_ALREADY_EXIST_BY_EMAIL("해당 이메일로 가입된 유저가 이미 존재합니다.");


    private final String message;
    private static final String prefix = "[ERROR] ";


    ErrorMessages(String message) {
        this.message = prefix + message;
    }

    public String getMessage() {
        return message;
    }
}


